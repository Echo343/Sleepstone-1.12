package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.gems.Gem;

public enum Slots {
	Stone(ModItems.itemStoneGem),
	StoneEthereal(ModItems.itemEtherealGem),
	StoneGuardian(ModItems.itemGuardianGem),
	StoneFire(ModItems.itemFireGem),
	TimeSpace(ModItems.itemTimeSpaceGem),
	TimeSpaceEthereal(ModItems.itemEtherealGem),
	TimeSpaceGuardian(ModItems.itemGuardianGem),
	TimeSpaceFire(ModItems.itemFireGem),
	Pathfinder(ModItems.itemPathfinderGem),
	PathfinderEthereal(ModItems.itemEtherealGem),
	PathfinderGuardian(ModItems.itemGuardianGem),
	PathfinderFire(ModItems.itemFireGem);
	
	private Gem gem;
	private Class<? extends Gem> type;
	
	Slots(Gem item) {
		this.gem = item;
		this.type = gem.getClass();
	}
	
	public Gem getItem() {
		return gem;
	}
	
	public Class<? extends Gem> getGemType() {
		return type;
	}
}
