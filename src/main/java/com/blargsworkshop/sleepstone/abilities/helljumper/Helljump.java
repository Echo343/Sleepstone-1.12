package com.blargsworkshop.sleepstone.abilities.helljumper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum Helljump {
	INSTANCE;
	
	public BlockPos findSafeSpot(World world, BlockPos destinationPoint) {
		BlockPos safeLocation = null;
		List<BlockPos> bubble = getShape();
		Iterator<BlockPos> iterSearchPattern = getSearchPattern(destinationPoint).iterator();
		while (iterSearchPattern.hasNext()) {
			BlockPos searchLocation = iterSearchPattern.next();
			if (isLocationSafe(world, searchLocation, bubble)) {
				safeLocation = searchLocation;
				break;
			}
		}
		return safeLocation;
	}
	
	private List<BlockPos> getSearchPattern(BlockPos startingPoint) {
		return null;
	}

	@SuppressWarnings("deprecation")
	private boolean isLocationSafe(World world, BlockPos location, List<BlockPos> shape) {
		// Check the shape, these blocks can only be replaceables
		for (BlockPos shapePiece: shape) {
			BlockPos pos = location.add(shapePiece.getX(), shapePiece.getY(), shapePiece.getZ());
			if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
				return false;
			}
		}
		
		// Check the place where the player stands, must be only replaceable
		for (int i = 0; i < 2; i++) {
			BlockPos pos = location.add(0, i, 0);
			if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
				return false;
			}
		}
		
		// Check the floor, must be a replaceable or a solid.
		BlockPos pos = location.add(0, -1, 0);
		IBlockState state = world.getBlockState(pos);
		Block groundBlock = state.getBlock();
		if (!groundBlock.isReplaceable(world, pos)) {
			if (!groundBlock.isTopSolid(state)) {
				return false;
			}
		}
		
		return true;
	}
	
	// TODO abstract the shape obj, here and in Nature Wall
	private List<BlockPos> getShape() {
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
