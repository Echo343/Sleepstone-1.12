package com.blargsworkshop.sleepstone.abilities.helljumper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.blargsworkshop.engine.sound.SoundManager;
import com.blargsworkshop.engine.utility.SimpleTeleporter;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.ModItems.Sounds;
import com.blargsworkshop.sleepstone.items.stone.WarpSicknessPotionEffect;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class Helljump {
	
	private final List<BlockPos> bubbleShape = getBubbleShape();
	private final EntityPlayerMP player;
	private final BlockPos departLocation;
	private final DimensionType destinationDim;
	private final World destWorld;
	
	public Helljump(EntityPlayerMP player) {
		this.player = player;
		departLocation = player.getPosition();
		if (player.dimension == DimensionType.OVERWORLD.getId()) {
			destinationDim = DimensionType.NETHER;
		}
		else {
			destinationDim = DimensionType.OVERWORLD;
		}
		MinecraftServer minecraftserver = player.getServer();
		destWorld = minecraftserver.getWorld(destinationDim.getId());
	}
	
	public void startJump() {
		if (player.isPotionActive(Potions.warpSickness)) {
			Utils.addStatusMessage(player, "text.sleepstone.suffering_effects_of_warping");
		}
		else {
			if (player.dimension == DimensionType.NETHER.getId() || player.dimension == DimensionType.OVERWORLD.getId()) {
				if (!tryJump()) {
					Utils.addChatMessage(player, "text.helljump.fizzle");
				}
			}
			else {
				Utils.addChatMessage(player, "text.helljump.not.dimension.attuned");
			}
		}
	}
	
	protected boolean tryJump() {
		boolean wasJumpSuccessful = false;
		
		BlockPos destinationPoint = calcJumpPoint(destinationDim);
		BlockPos safePoint = findSafeSpot(destinationPoint);
		if (safePoint != null) {
			createBubble(safePoint);
			SoundManager.playSoundAtEntityFromServer(player, Sounds.swoosh);
			SimpleTeleporter.INSTANCE.teleportPlayerToDimension(player, destinationDim, safePoint);
			player.addPotionEffect(new WarpSicknessPotionEffect(30));
			SoundManager.playSoundAtEntityFromServer(player, Sounds.teleport);
			wasJumpSuccessful = true;
			
			BlockPos difference = safePoint.subtract(destinationPoint);
			Utils.addChatMessage(player, "text.helljump.offset", difference.getX(), difference.getY(), difference.getZ());
		}
		
		return wasJumpSuccessful;
	}
	
	protected BlockPos calcJumpPoint(DimensionType destinationDimension) {
		int x, y, z;
		if (destinationDimension == DimensionType.NETHER) {
			// to Nether
			x = departLocation.getX() / 8;
			z = departLocation.getZ() / 8;
		}
		else {
			// to Overworld
			x = departLocation.getX() * 8;
			z = departLocation.getZ() * 8;
		}
		y = departLocation.getY();
		return new BlockPos(x, y, z);
	}
	
	protected void createBubble(BlockPos p) {
		BlockPos pos;
		// Create bubble of glass
		for (BlockPos part : bubbleShape) {
			pos = p.add(part.getX(), part.getY(), part.getZ());
			if (destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
				destWorld.setBlockState(pos, Blocks.GLASS.getDefaultState());
			}
		}
		
		// Set to air where the player occupies
		for (int i = 0; i < 2; i++) {
			pos = p.add(0, i, 0);
			destWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		
		// Create flooring block if needed
		pos = p.add(0, -1, 0);
		if (destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
			destWorld.setBlockState(pos, Blocks.GLASS.getDefaultState());
		}
	}
	
	private BlockPos findSafeSpot(BlockPos destinationPoint) {
		BlockPos safeLocation = null;
		BlockPos searchLocation;
		Iterator<BlockPos> iterSearchPattern = new SearchPattern(destinationPoint);
		while (iterSearchPattern.hasNext()) {
			searchLocation = iterSearchPattern.next();
			if (isLocationSafe(searchLocation)) {
				safeLocation = searchLocation;
				break;
			}
		}
		return safeLocation;
	}
	
	@SuppressWarnings("deprecation")
	private boolean isLocationSafe(BlockPos location) {
		// TODO allow partial bubbles.
		// Check the shape, these blocks can only be replaceables
		BlockPos pos;
		for (BlockPos shapePiece: bubbleShape) {
			pos = location.add(shapePiece.getX(), shapePiece.getY(), shapePiece.getZ());
			if (
					!destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos) &&
					!destWorld.getBlockState(pos).isNormalCube() &&
					!destWorld.getBlockState(pos).getBlock().equals(Blocks.GLASS)) {
				return false;
			}
		}
		
		// Check the place where the player occupies, must be only replaceable
		for (int i = 0; i < 2; i++) {
			pos = location.add(0, i, 0);
			if (!destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
				return false;
			}
		}
		
		// Check the floor, must be a replaceable or a solid.
		pos = location.add(0, -1, 0);
		IBlockState state = destWorld.getBlockState(pos);
		Block groundBlock = state.getBlock();
		if (
				!groundBlock.isReplaceable(destWorld, pos) &&
				!state.isTopSolid() &&
				!groundBlock.equals(Blocks.GLASS)) {
			return false;
		}
		
		return true;
	}
	
	// TODO abstract the shape obj, here and in Nature Wall
	protected List<BlockPos> getBubbleShape() {
		List<BlockPos> shape = new ArrayList<>();
		shape.add(new BlockPos(0, 0, 1));
		shape.add(new BlockPos(1, 0, 0));
		shape.add(new BlockPos(0, 0, -1));
		shape.add(new BlockPos(-1, 0, 0));
		shape.add(new BlockPos(0, 1, 1));
		shape.add(new BlockPos(1, 1, 0));
		shape.add(new BlockPos(0, 1, -1));
		shape.add(new BlockPos(-1, 1, 0));
		shape.add(new BlockPos(0, 2, 0));
		return shape;
	}

}
