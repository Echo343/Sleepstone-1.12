package com.blargsworkshop.sleepstone;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class BlargsMod {
	
//	protected static Supplier<BlargsMod> getModInstanceFunction;
//	protected abstract IProxy getBlargProxy();
//	
//	public static BlargsMod getInstance() {
//		return getModInstanceFunction.get();
//	}
//	
//	public static IProxy getProxy() {
//		return getInstance().getBlargProxy();
//	}
//	
	public abstract void preInit(FMLPreInitializationEvent e);
	public abstract void init(FMLInitializationEvent e);
	public abstract void postInit(FMLPostInitializationEvent e);
}
