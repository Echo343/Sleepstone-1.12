package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class BlargsMod {
	
	protected static BlargsMod instance;
	protected abstract IProxy getBlargProxy();
	
	public static IProxy getProxy() {
		return instance.getBlargProxy();
	}
	
	public abstract void preInit(FMLPreInitializationEvent e);
	public abstract void init(FMLInitializationEvent e);
	public abstract void postInit(FMLPostInitializationEvent e);
}
