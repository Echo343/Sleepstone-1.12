package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class BlargsMod {
	
	protected static BlargsMod instance;

	protected abstract IProxy getCommonProxy();
	protected abstract IProxy getClientProxy();
	
	public IProxy getProxy() {
		if (getClientProxy() != null) {
			return getClientProxy();
		}
		return getCommonProxy();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Log.detail("PreInit Start");
		getCommonProxy().preInit(e);
		if (getClientProxy() != null) {
			getClientProxy().preInit(e);
		}
		Log.detail("PreInit End");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		Log.info("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!");
		Log.detail("Init Start");
		getCommonProxy().init(e);
		if (getClientProxy() != null) {
			getClientProxy().init(e);
		}
		Log.detail("Init End");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Log.detail("PostInit Start");
		getCommonProxy().postInit(e);
		if (getClientProxy() != null) {
			getClientProxy().postInit(e);
		}
		Log.detail("PostInit End");
	}
}
