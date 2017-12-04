package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Log.detail("PreInit Start");
		getProxy().preInit(e);
		Log.detail("PreInit End");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Log.info("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!");
		Log.detail("Init Start");
		getProxy().init(e);
		Log.detail("Init End");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Log.detail("PostInit Start");
		getProxy().postInit(e);
		Log.detail("PostInit End");
	}
	
	
	
}
