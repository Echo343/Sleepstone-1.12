package com.blargsworkshop.sleepstone.abilities.helljumper;

import java.util.Iterator;

import net.minecraft.util.math.BlockPos;

class SearchPattern implements Iterator<BlockPos> {
	
	private static final int RADIUS = 3;
	private final BlockPos sourcePoint;
	private int blockCount = 0;
	private int radH = 0;
	private int radY = 0;
	private int x = 0;
	private int y = 0;
	private int z = 0;

	public SearchPattern(BlockPos destinationPoint) {
		this.sourcePoint = destinationPoint;
	}

	@Override
	public boolean hasNext() {
		return radY <= RADIUS;
	}

	@Override
	public BlockPos next() {
		BlockPos currentBlockPos = sourcePoint.add(x, y, z);
		
		if ((x == 0 && z == 0) || (x == -1 && z == radH)) {
			if (y == 0) {
				radH = 0;
				y = ++radY;
				x = 0;
				z = 0;
			}
			else if (y > 0) {
				flipY();
				x = 0;
			}
			else if (y < 0) {
				flipY();
				down();
				radH++;
				x = 0;
				z += 1;
			}
		}
		else if (z == radH && x != radH) {
			east();
		}
		else if (x == radH && z != -radH) {
			south();
		}
		else if (z == -radH && x != -radH) {
			west();
		}
		else if (x == -radH && z != radH) {
			north();
		}
		
		blockCount++;
		return currentBlockPos;
	}
	
	public int getBlockCount() {
		return blockCount;
	}
	
	private void east() {
		x += 1;
	}
	private void west() {
		x -= 1;
	}
	private void north() {
		z += 1;
	}
	private void south() {
		z -= 1;
	}
	private void down() {
		y -= 1;
	}
	private void flipY() {
		y *= -1;
	}

}
