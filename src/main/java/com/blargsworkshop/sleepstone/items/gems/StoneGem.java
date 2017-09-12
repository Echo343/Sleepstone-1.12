package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.ModInfo;

/***
 * A gem of unusual hardness.  It feels heavier than it should.
 * @author Echo343
 *
 */
public class StoneGem extends Gem {

	private static final String UNLOCALIZEDNAME = "stonegem";
	private static final String TEXTURE = ModInfo.ID + ":gem-stone";
	
	public StoneGem() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

}
