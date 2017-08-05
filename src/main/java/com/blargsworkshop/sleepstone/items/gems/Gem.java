package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.items.BaseItem;

public abstract class Gem extends BaseItem {

	public Gem(String unlocalizedName, String texture) {
		super(unlocalizedName, texture);
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(16);
	}
}
