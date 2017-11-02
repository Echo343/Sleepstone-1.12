package com.blargsworkshop.sleepstone.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IProxy {
	
	public void preInit(FMLPreInitializationEvent e);
    public void init(FMLInitializationEvent e);
    public void postInit(FMLPostInitializationEvent e);
    public EntityPlayer getPlayerEntity(MessageContext ctx);
    // req. 1.8+
    public net.minecraft.util.IThreadListener getThreadFromContext(MessageContext ctx);
}
