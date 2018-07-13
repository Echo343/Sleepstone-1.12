package com.blargsworkshop.sleepstone.abilities.helljumper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.blargsworkshop.engine.sound.SoundManager;
import com.blargsworkshop.engine.utility.SimpleTeleporter;
import com.blargsworkshop.sleepstone.ModItems.Sounds;
import com.blargsworkshop.sleepstone.items.stone.WarpSicknessPotionEffect;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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
		destWorld = DimensionManager.getWorld(destinationDim.getId());
	}
	
	public boolean tryJump() {
		boolean wasJumpSuccessful = false;
		
		BlockPos destinationPoint = calcJumpPoint(destinationDim);
		// TODO not sure if I have to load the world chunks.
		BlockPos safePoint = findSafeSpot(destinationPoint);
		if (safePoint != null) {
			createBubble(safePoint);
			SoundManager.playSoundAtEntityFromServer(player, Sounds.swoosh);
			SimpleTeleporter.INSTANCE.teleportPlayerToDimension(player, destinationDim, safePoint);
			player.addPotionEffect(new WarpSicknessPotionEffect(30));
			SoundManager.playSoundAtEntityFromServer(player, Sounds.teleport);
			wasJumpSuccessful = true;
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
		for (BlockPos part : bubbleShape) {
			pos = p.add(part.getX(), part.getY(), part.getZ());
			destWorld.setBlockState(pos, Blocks.GLASS.getDefaultState());
		}
		
		// Create flooring block if needed
		pos = p.add(0, -1, 0);
		if (destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
			destWorld.setBlockState(pos, Blocks.GLASS.getDefaultState());
		}
	}
	
	private BlockPos findSafeSpot(BlockPos destinationPoint) {
		BlockPos safeLocation = null;
		Iterator<BlockPos> iterSearchPattern = new SearchPattern(destinationPoint);
		while (iterSearchPattern.hasNext()) {
			BlockPos searchLocation = iterSearchPattern.next();
			if (isLocationSafe(searchLocation)) {
				safeLocation = searchLocation;
				break;
			}
		}
		return safeLocation;
	}
	
	@SuppressWarnings("deprecation")
	private boolean isLocationSafe(BlockPos location) {
		// Check the shape, these blocks can only be replaceables
		for (BlockPos shapePiece: bubbleShape) {
			BlockPos pos = location.add(shapePiece.getX(), shapePiece.getY(), shapePiece.getZ());
			if (!destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
				return false;
			}
		}
		
		// Check the place where the player stands, must be only replaceable
		for (int i = 0; i < 2; i++) {
			BlockPos pos = location.add(0, i, 0);
			if (!destWorld.getBlockState(pos).getBlock().isReplaceable(destWorld, pos)) {
				return false;
			}
		}
		
		// Check the floor, must be a replaceable or a solid.
		BlockPos pos = location.add(0, -1, 0);
		IBlockState state = destWorld.getBlockState(pos);
		Block groundBlock = state.getBlock();
		if (!groundBlock.isReplaceable(destWorld, pos)) {
			if (!groundBlock.isTopSolid(state)) {
				return false;
			}
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
