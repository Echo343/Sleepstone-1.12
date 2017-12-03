package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;

/**
 * Main mod class
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, acceptedMinecraftVersions = ModInfo.ACCEPTED_MINECRAFT_VERSIONS)
public class SleepstoneMod extends BlargsMod {

	public SleepstoneMod() {
		BlargsMod.instance = instance;
	}
	
	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(serverSide = ModInfo.COMMON_PROXY, clientSide = ModInfo.CLIENT_PROXY)
	public static IProxy proxy;

	@Override
	protected IProxy getBlargProxy() {
		return proxy;
	}

	
	
	
	
}
