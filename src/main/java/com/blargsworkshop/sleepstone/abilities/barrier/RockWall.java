package com.blargsworkshop.sleepstone.abilities.barrier;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RockWall {
	
	public void generate(EntityPlayerMP player) {
		World world = player.getEntityWorld();
		BlockPos playerPosition = player.getPosition();
		List<BlockPos> shape = getShape();
		
		for (int y = -1; y <= 2; y++) {
			for (BlockPos shapeFragment : shape) {
				BlockPos p = playerPosition.add(shapeFragment.getX(), y, shapeFragment.getZ());
				if (world.getBlockState(p).getBlock().isReplaceable(world, p)) {
					world.setBlockState(p, Blocks.LEAVES.getDefaultState());
				}
			}
		}
	}
	
	protected List<BlockPos> getShape() {
		ArrayList<BlockPos> shape = new ArrayList<>();
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
		return shape;
	}

}
