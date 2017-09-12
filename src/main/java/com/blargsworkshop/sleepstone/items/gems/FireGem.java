package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.ModInfo;

public class FireGem extends Gem {

	private static final String UNLOCALIZEDNAME = "firegem";
	private static final String TEXTURE = ModInfo.ID + ":gem-fire";
	
	public FireGem() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

}
