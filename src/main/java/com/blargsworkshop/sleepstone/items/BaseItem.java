package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.item.Item;

public abstract class BaseItem extends Item {
	
	private final String name;
	
	/**
	 * Creates a new item.
	 * @param unlocalizedName - name part of the message key for the item
	 * @param registryName - this is the filename of the .json file for the model without the ".json". This will also be used for name in the item registry.
	 */
	public BaseItem(String unlocalizedName, String registryName) {
		this.name = unlocalizedName;
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(ModItems.tabSleepstone);
	}
	
	public String getName() {
		return name;
	}
	
//	/**
//	 * Used for items that have a different texture for each subtype.
//	 * @param unlocalizedName
//	 */
//	public BaseItem(String unlocalizedName) {
//		this.setUnlocalizedName(unlocalizedName);
//		this.setCreativeTab(ModItems.tabSleepstone);
//	}
}
