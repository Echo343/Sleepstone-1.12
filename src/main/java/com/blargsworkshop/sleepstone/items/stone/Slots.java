package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.gems.EtherealGem;
import com.blargsworkshop.sleepstone.items.gems.FireGem;
import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.gems.GuardianGem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.StoneGem;
import com.blargsworkshop.sleepstone.items.gems.TimeSpaceGem;

import net.minecraft.item.Item;

public enum Slots {
	Stone(StoneGem.class, ModItems.itemStoneGem),
	StoneEthereal(EtherealGem.class, ModItems.itemEtherealGem),
	StoneGuardian(GuardianGem.class, ModItems.itemGuardianGem),
	StoneFire(FireGem.class, ModItems.itemFireGem),
	TimeSpace(TimeSpaceGem.class, ModItems.itemTimeSpaceGem),
	TimeSpaceEthereal(EtherealGem.class, ModItems.itemEtherealGem),
	TimeSpaceGuardian(GuardianGem.class, ModItems.itemGuardianGem),
	TimeSpaceFire(FireGem.class, ModItems.itemFireGem),
	Pathfinder(PathfinderGem.class, ModItems.itemPathfinderGem),
	PathfinderEthereal(EtherealGem.class, ModItems.itemEtherealGem),
	PathfinderGuardian(GuardianGem.class, ModItems.itemGuardianGem),
	PathfinderFire(FireGem.class, ModItems.itemFireGem);
	
	private Item gem;
	private Class<? extends Gem> type;
	
	Slots(Class<? extends Gem> type, Item item) {
		this.gem = item;
		this.type = type;
	}
	
	public Item getItem() {
		return gem;
	}
	
	public Class<? extends Gem> getGemType() {
		return type;
	}
}
