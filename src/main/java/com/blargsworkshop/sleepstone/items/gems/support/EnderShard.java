package com.blargsworkshop.sleepstone.items.gems.support;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.BaseItem;

public class EnderShard extends BaseItem {

	private static final String UNLOCALIZEDNAME = "endershard";
	private static final String TEXTURE = ModInfo.ID + ":endershard";
	
	public EnderShard() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

}
