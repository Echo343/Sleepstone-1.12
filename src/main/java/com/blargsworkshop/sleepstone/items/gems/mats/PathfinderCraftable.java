package com.blargsworkshop.sleepstone.items.gems.mats;

import com.blargsworkshop.sleepstone.items.BaseSubtype;

/**
 * item.pathfindercraftable_0.name=Mob Essence
 * 
 * @author Echo343
 * 
 */
public class PathfinderCraftable extends BaseSubtype {

	private static final String UNLOCALIZED_NAME = "craftable_pathfinder";
	private static final String REGISTRY_NAME = "craftable_pathfinder";
	private static final int NUMBER_OF_CRAFTABLES = 1;

	public PathfinderCraftable() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME, NUMBER_OF_CRAFTABLES);
	}
}
