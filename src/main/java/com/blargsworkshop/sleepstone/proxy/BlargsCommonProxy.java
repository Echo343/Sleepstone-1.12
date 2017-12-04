package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.IModItems;
import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.events.RegisterModComponents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BlargsCommonProxy implements IProxy {

	public abstract void registerCapabilities();
	public abstract void registerEventHandlers();
	public abstract void registerGuiHandlers();
	public abstract void registerPackets();
	protected abstract Class<? extends IModItems> getModItemClass();
	
	/**
	 * Helper method to register a guiHandler.  Call this from registerGuiHandlers
	 * @param modInstance
	 * @param guiHandler
	 */
	protected void registerGuiHandlerHelper(Object modInstance, IGuiHandler guiHandler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(modInstance, guiHandler);		
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
		Log.detail("BlargsCommonProxy - PreInit");
		MinecraftForge.EVENT_BUS.register(new RegisterModComponents(getModItemClass()));
    }

    @Override
    public void init(FMLInitializationEvent e) {
		Log.detail("BlargsCommonProxy - Init");
		
    	//Capabilities
    	registerCapabilities();
    	
    	// Event Handlers
    	registerEventHandlers();
    	
    	// GUI Handler
    	registerGuiHandlers();
    	
    	// Network Packets
    	registerPackets();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
		Log.detail("BlargsCommonProxy - PostInit");
    }
    
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
    	Log.detail("Retrieving player from CommonProxy for message on side " + ctx.side);
    	return ctx.getServerHandler().player;
    }
    
    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServerWorld();
	}
}
