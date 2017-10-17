package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.item.Item;

public abstract class BaseItem extends Item {

	/**
	 * Creates a new item.
	 * @param unlocalizedName - name part of the message key for the item
	 * @param texture - texture of the item in the form of "ModInfo.ID + ":<filename>""
	 */
	public BaseItem(String unlocalizedName, String texture) {
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(texture);
		this.setCreativeTab(ModItems.tabSleepstone);
	}

	/**
	 * Used for items that have a different texture for each subtype.
	 * @param unlocalizedName
	 */
	public BaseItem(String unlocalizedName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(ModItems.tabSleepstone);
	}
}
