package com.blargsworkshop.engine;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IBlargsMod {
	public void preInit(FMLPreInitializationEvent e);
	public void init(FMLInitializationEvent e);
	public void postInit(FMLPostInitializationEvent e);
}
