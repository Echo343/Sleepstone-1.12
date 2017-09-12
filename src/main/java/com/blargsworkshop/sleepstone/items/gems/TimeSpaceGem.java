/**
 * 
 */
package com.blargsworkshop.sleepstone.items.gems;

import com.blargsworkshop.sleepstone.ModInfo;

/**
 * Time and Space Gem
 * 
 * @author Echo343
 *
 */
public class TimeSpaceGem extends Gem {

	private static final String UNLOCALIZEDNAME = "timespacegem";
	private static final String TEXTURE = ModInfo.ID + ":gem-time-and-space";
	
	public TimeSpaceGem() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

}
