package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.engine.item.BaseItem;
import com.blargsworkshop.sleepstone.ModItems;

public abstract class BaseModItem extends BaseItem {
		
	/**
	 * Creates a new item.
	 * @param unlocalizedName - name part of the message key for the item
	 * @param registryName - this is the filename of the .json file for the model without the ".json". This will also be used for name in the item registry.
	 */
	public BaseModItem(String unlocalizedName, String registryName) {
		super(unlocalizedName, registryName);
		this.setCreativeTab(ModItems.tabSleepstone);
	}
}
