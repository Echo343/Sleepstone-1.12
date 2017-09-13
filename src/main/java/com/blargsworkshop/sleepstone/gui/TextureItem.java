package com.blargsworkshop.sleepstone.gui;

import net.minecraft.item.Item;

public class TextureItem extends Item {

	public TextureItem(String unlocalizedName, String texture) {
		this.setUnlocalizedName(unlocalizedName);
		this.setTextureName(texture);
	}

}
