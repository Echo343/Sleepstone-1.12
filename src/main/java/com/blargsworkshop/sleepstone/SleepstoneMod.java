package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SleepstoneMod {

	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static IProxy proxy;

	/**
	 * Read Config, create blocks, items, etc & register them.
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Log.detail("PreInit Start");
		proxy.preInit(e);
		Log.detail("PreInit End");
	}

	/**
	 * Build up data structures, add Crafting Recipes, and register new handlers.
	 * @param e
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		Log.info("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!");
		Log.detail("Init Start");
		proxy.init(e);
		Log.detail("Init End");
	}

	/**
	 * Communicate with other mods and adjust setup.
	 * @param e
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Log.detail("PostInit Start");
		proxy.postInit(e);
		Log.detail("PostInit End");
	}
}
