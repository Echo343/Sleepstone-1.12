package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.gems.Gem;

public enum GemSlot {
	MONK_1(ModItems.itemMindBodyGem),
	MONK_2(ModItems.itemMindBodyGem),
	MONK_3(ModItems.itemMindBodyGem),
	TIMESPACE_1(ModItems.itemTimeSpaceGem),
	TIMESPACE_2(ModItems.itemTimeSpaceGem),
	TIMESPACE_3(ModItems.itemTimeSpaceGem),
	PATHFINDER_1(ModItems.itemPathfinderGem),
	PATHFINDER_2(ModItems.itemPathfinderGem),
	PATHFINDER_3(ModItems.itemPathfinderGem),
	ETHEREAL_1(ModItems.itemEtherealGem),
	ETHEREAL_2(ModItems.itemEtherealGem),
	ETHEREAL_3(ModItems.itemEtherealGem),
	GUARDIAN_1(ModItems.itemGuardianGem),
	GUARDIAN_2(ModItems.itemGuardianGem),
	GUARDIAN_3(ModItems.itemGuardianGem),
	ELEMENTAL_1(ModItems.itemElementalGem),
	ELEMENTAL_2(ModItems.itemElementalGem),
	ELEMENTAL_3(ModItems.itemElementalGem);
	
	private Gem gem;
	
	GemSlot(Gem item) {
		this.gem = item;
	}
	
	public Gem getGemItem() {
		return gem;
	}
}
