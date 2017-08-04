package com.blargsworkshop.sleepstone;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e);
    public void init(FMLInitializationEvent e);
    public void postInit(FMLPostInitializationEvent e);
    
}
