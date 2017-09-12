package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.ModInfo;

public class PathfinderGem extends Gem {
	private static final String UNLOCALIZEDNAME = "pathfindergem";
	private static final String TEXTURE = ModInfo.ID + ":gem-pathfinder";
	
	public PathfinderGem() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}
}
