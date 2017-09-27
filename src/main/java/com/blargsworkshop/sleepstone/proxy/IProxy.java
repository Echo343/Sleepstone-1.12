package com.blargsworkshop.sleepstone.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public interface IProxy {
	
	public void preInit(FMLPreInitializationEvent e);
    public void init(FMLInitializationEvent e);
    public void postInit(FMLPostInitializationEvent e);
    public EntityPlayer getPlayerEntity(MessageContext ctx);
    // req. 1.8+
//    public net.minecraft.util.IThreadListener getThreadFromContext(MessageContext ctx);
}
