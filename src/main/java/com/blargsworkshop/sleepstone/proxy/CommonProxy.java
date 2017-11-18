package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.events.MainEventHandler;
import com.blargsworkshop.sleepstone.events.RegisterModComponents;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy implements IProxy {

	@Override
    public void preInit(FMLPreInitializationEvent e) {
    }

    @Override
    public void init(FMLInitializationEvent e) {
    	RegisterModComponents.initSmeltingRecipes();
		ModItems.preInitPotions();
		
    	// Event Handlers
    	MinecraftForge.EVENT_BUS.register(new MainEventHandler());
//    	FMLCommonHandler.instance().bus().register(new FMLEventHandler());
    	
    	// GUI Handler
    	NetworkRegistry.INSTANCE.registerGuiHandler(SleepstoneMod.instance, new GuiHandler());
    	
		PacketDispatcher.registerPackets();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
    }
    
    /**
     * Returns a side-appropriate EntityPlayer for use during message handling
     * @param ctx
     * @return
     */
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
    	Log.detail("Retrieving player from CommonProxy for message on side " + ctx.side);
    	return ctx.getServerHandler().player;
    }
    
    /**
     * Returns the current thread based on side during message handling,
     * used for ensuring that the message is being handled by the main thread.
     * req. 1.8+ only
     * @param ctx
     * @return
     */
    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServerWorld();
	}
}
