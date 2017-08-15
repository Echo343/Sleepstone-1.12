package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.items.stone.ItemSleepstone;
import com.blargsworkshop.sleepstone.network.BasicMessage;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IProxy {

	@Override
    public void preInit(FMLPreInitializationEvent e) {
		ModItems.initCreativeTabs();
		ModItems.init();
    }

    @Override
    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(SleepstoneMod.instance, new GuiHandler());
    	SleepstoneMod.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);
    	SleepstoneMod.networkWrapper.registerMessage(ItemSleepstone.class, BasicMessage.class, 0, Side.SERVER); //TODO ? id should be dynamically generated?
		ModItems.initRecipes();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }
}
