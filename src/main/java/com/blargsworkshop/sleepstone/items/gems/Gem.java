package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.items.BaseItem;

public abstract class Gem extends BaseItem {

	public Gem(String unlocalizedName, String registeryName) {
		super(unlocalizedName, registeryName);
		this.setMaxStackSize(16);
	}
}
