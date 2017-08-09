/**
 * 
 */
package com.blargsworkshop.sleepstone;

/**
 * Class that contains all the Mod info
 * 
 * @author Echo343
 *
 */
public class ModInfo {
	public static final String ID = "sleepstonemod";
	public static final String NAME = "Sleepstone";
	public static final String VERSION = "1.2";
	public static final String CLIENT_PROXY = "com.blargsworkshop.sleepstone.proxy.ClientProxy";
	public static final String COMMON_PROXY = "com.blargsworkshop.sleepstone.proxy.CommonProxy";
	public static final String CHANNEL = "";
	
	public static enum DEBUG {
		NONE, CASUAL, DETAIL
	};
	
	public static DEBUG DEBUG_LVL = DEBUG.DETAIL;
	public static boolean DEBUG_CHAT = true;
	
	public static final String CREATIVE_TAB_SLEEPSTONE = "sleepstoneCreativeTab";
}
