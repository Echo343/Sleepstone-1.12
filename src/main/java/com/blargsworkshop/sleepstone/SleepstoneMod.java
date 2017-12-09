package com.blargsworkshop.sleepstone;

import com.blargsworkshop.engine.IBlargsMod;
import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Main mod class
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, acceptedMinecraftVersions = ModInfo.ACCEPTED_MINECRAFT_VERSIONS)
public class SleepstoneMod implements IBlargsMod {

//	public static Supplier<BlargsMod> getInstanceFunction() {
//		return () -> SleepstoneMod.instance;
//	}
	
//	public SleepstoneMod() {
//		BlargsMod.getModInstanceFunction = getModInstanceFunction();
//	}
	
	public static IProxy getProxy() {
		return proxy;
	}
	
	public static SleepstoneMod getInstance() {
		return instance;
	}
	
	@Instance
	private static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(serverSide = ModInfo.COMMON_PROXY, clientSide = ModInfo.CLIENT_PROXY)
	private static IProxy proxy;

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Log.detail("PreInit Start");
		getProxy().preInit(e);
		Log.detail("PreInit End");
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent e) {
		Log.info("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!");
		Log.detail("Init Start");
		getProxy().init(e);
		Log.detail("Init End");
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Log.detail("PostInit Start");
		getProxy().postInit(e);
		Log.detail("PostInit End");
	}

}
