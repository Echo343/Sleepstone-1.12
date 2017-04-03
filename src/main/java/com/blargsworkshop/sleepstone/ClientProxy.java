package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.Gui.GuiHandler;
import com.blargsworkshop.sleepstone.interfaces.IProxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ClientProxy implements IProxy {
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
		
    }

    @Override
    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(SleepstoneMod.instance, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
    	
    }
}
