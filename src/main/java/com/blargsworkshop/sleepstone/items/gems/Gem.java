package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.items.BaseModItem;

public abstract class Gem extends BaseModItem {

	public Gem(String unlocalizedName, String registeryName) {
		super(unlocalizedName, registeryName);
		this.setMaxStackSize(16);
	}
}
