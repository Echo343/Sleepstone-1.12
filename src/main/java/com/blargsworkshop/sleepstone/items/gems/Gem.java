package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.SleepstoneMod;

import net.minecraft.item.Item;

public abstract class Gem extends Item {

	public Gem(String unlocalizedName, String texture) {
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(16);
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(texture);
		this.setCreativeTab(SleepstoneMod.tabSleepstone);
	}
}
