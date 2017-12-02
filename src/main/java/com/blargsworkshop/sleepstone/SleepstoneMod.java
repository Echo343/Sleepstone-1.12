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
	
	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(serverSide = ModInfo.COMMON_PROXY)
	public static IProxy commonProxy;
	
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY)
	public static IProxy clientProxy;
	
	
	@Override
	protected IProxy getCommonProxy() {
		return commonProxy;
	}

	@Override
	protected IProxy getClientProxy() {
		return clientProxy;
	}

	
	
	
	
}
