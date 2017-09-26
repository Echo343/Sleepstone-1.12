package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.events.MainEventHandler;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.network.BasicMessage;
import com.blargsworkshop.sleepstone.network.NetworkHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IProxy {

	@Override
    public void preInit(FMLPreInitializationEvent e) {
		ModItems.initCreativeTabs();
		ModItems.init();
    }

    @Override
    public void init(FMLInitializationEvent e) {
    	// Event Handlers
    	MinecraftForge.EVENT_BUS.register(new MainEventHandler());
//    	FMLCommonHandler.instance().bus().register(new FMLEventHandler());
    	
    	// GUI Handler
    	NetworkRegistry.INSTANCE.registerGuiHandler(SleepstoneMod.instance, new GuiHandler());
    	
    	// Network Handler for sending packets
    	NetworkHandler.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);
    	NetworkHandler.networkWrapper.registerMessage(NetworkHandler.class, BasicMessage.class, 0, Side.SERVER);
    	
		ModItems.initRecipes();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
