package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class BlargsMod {
	
	protected static BlargsMod instance;
	protected abstract IProxy getBlargProxy();
	
	public static IProxy getProxy() {
		return instance.getBlargProxy();
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
