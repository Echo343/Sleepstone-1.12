package com.blargsworkshop.sleepstone.abilities.barrier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NatureWall {
	
	public void generate(EntityPlayerMP player) {
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();
		List<BlockPos> shape = getShape();
		
		for (int y = -1; y <= 2; y++) {
			// TODO make a different kind of leaves.
			for (BlockPos shapeFragment : shape) {
				BlockPos p = playerPosition.add(shapeFragment.getX(), y, shapeFragment.getZ());
				if (world.getBlockState(p).getBlock().isReplaceable(world, p)) {
					world.setBlockState(p, Blocks.LEAVES.getDefaultState());
				}
			}
			for ( Entry<EnumFacing, List<BlockPos>> direction : getVinesMap().entrySet()) {
				for (BlockPos vine : direction.getValue()) {
					BlockPos p = playerPosition.add(vine.getX(), y, vine.getZ());
					if (world.getBlockState(p).getBlock().isReplaceable(world, p)) {
						world.setBlockState(p, Blocks.VINE.getStateForPlacement(null, p, direction.getKey(), 0, 0, 0, 0, null, null));
					}
				}
			}
		}
	}
	
	protected Map<EnumFacing, List<BlockPos>> getVinesMap() {
		// TODO make enumMap
		Map<EnumFacing, List<BlockPos>> vines = new HashMap<>();
		List<BlockPos> north = new ArrayList<>();
		north.add(new BlockPos(-1, 0, 2));
		north.add(new BlockPos(0, 0, 2));
		north.add(new BlockPos(1, 0, 2));
		vines.put(EnumFacing.NORTH, north);
		
		List<BlockPos> east = new ArrayList<>();
		east.add(new BlockPos(-2, 0, 1));
		east.add(new BlockPos(-2, 0, 0));
		east.add(new BlockPos(-2, 0, -1));
		vines.put(EnumFacing.EAST, east);
		
		List<BlockPos> south = new ArrayList<>();
		south.add(new BlockPos(-1, 0, -2));
		south.add(new BlockPos(0, 0, -2));
		south.add(new BlockPos(1, 0, -2));
		vines.put(EnumFacing.SOUTH, south);
		
		List<BlockPos> west = new ArrayList<>();
		west.add(new BlockPos(2, 0, 1));
		west.add(new BlockPos(2, 0, 0));
		west.add(new BlockPos(2, 0, -1));
		vines.put(EnumFacing.WEST, west);
		
		return vines;
	}
	
	protected List<BlockPos> getShape() {
		ArrayList<BlockPos> shape = new ArrayList<>();
		//Inner ring
		shape.add(new BlockPos(0, 0, 3));
		shape.add(new BlockPos(1, 0, 3));
		shape.add(new BlockPos(2, 0, 2));
		shape.add(new BlockPos(3, 0, 1));
		shape.add(new BlockPos(3, 0, 0));
		shape.add(new BlockPos(3, 0, -1));
		shape.add(new BlockPos(2, 0, -2));
		shape.add(new BlockPos(1, 0, -3));
		shape.add(new BlockPos(0, 0, -3));
		shape.add(new BlockPos(-1, 0, -3));
		shape.add(new BlockPos(-2, 0, -2));
		shape.add(new BlockPos(-3, 0, -1));
		shape.add(new BlockPos(-3, 0, 0));
		shape.add(new BlockPos(-3, 0, 1));
		shape.add(new BlockPos(-2, 0, 2));
		shape.add(new BlockPos(-1, 0, 3));
		//Outer ring
		shape.add(new BlockPos(0, 0, 4));
		shape.add(new BlockPos(1, 0, 4));
		shape.add(new BlockPos(2, 0, 4));
		shape.add(new BlockPos(2, 0, 3));
		shape.add(new BlockPos(3, 0, 3));
		shape.add(new BlockPos(3, 0, 2));
		shape.add(new BlockPos(4, 0, 2));
		shape.add(new BlockPos(4, 0, 1));
		shape.add(new BlockPos(4, 0, 0));
		shape.add(new BlockPos(4, 0, -1));
		shape.add(new BlockPos(4, 0, -2));
		shape.add(new BlockPos(3, 0, -2));
		shape.add(new BlockPos(3, 0, -3));
		shape.add(new BlockPos(2, 0, -3));
		shape.add(new BlockPos(2, 0, -4));
		shape.add(new BlockPos(1, 0, -4));
		shape.add(new BlockPos(0, 0, -4));
		shape.add(new BlockPos(-1, 0, -4));
		shape.add(new BlockPos(-2, 0, -4));
		shape.add(new BlockPos(-2, 0, -3));
		shape.add(new BlockPos(-3, 0, -3));
		shape.add(new BlockPos(-3, 0, -2));
		shape.add(new BlockPos(-4, 0, -2));
		shape.add(new BlockPos(-4, 0, -1));
		shape.add(new BlockPos(-4, 0, 0));
		shape.add(new BlockPos(-4, 0, 1));
		shape.add(new BlockPos(-4, 0, 2));
		shape.add(new BlockPos(-3, 0, 2));
		shape.add(new BlockPos(-3, 0, 3));
		shape.add(new BlockPos(-2, 0, 3));
		shape.add(new BlockPos(-2, 0, 4));
		shape.add(new BlockPos(-1, 0, 4));
		return shape;
	}

}
