package com.blargsworkshop.engine.item;

import net.minecraft.item.Item;

public abstract class BaseItem extends Item {
	/**
	 * Creates a new item.
	 * @param unlocalizedName - name part of the message key for the item
	 * @param registryName - this is the filename of the .json file for the model without the ".json". This will also be used for name in the item registry.
	 */
	public BaseItem(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
	}
}
