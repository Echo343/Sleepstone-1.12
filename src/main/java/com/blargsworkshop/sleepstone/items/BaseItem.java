package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;

import net.minecraft.item.Item;

public class BaseItem extends Item {

	public BaseItem(String unlocalizedName, String texture) {
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(texture);
		this.setCreativeTab(ModItems.tabSleepstone);
	}

	public BaseItem(String unlocalizedName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(ModItems.tabSleepstone);
	}
}
