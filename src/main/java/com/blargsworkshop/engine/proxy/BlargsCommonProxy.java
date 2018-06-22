package com.blargsworkshop.engine.proxy;

import java.util.List;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.event.RegisterModComponents;
import com.blargsworkshop.engine.logger.Log;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BlargsCommonProxy implements IProxy {
	
	public abstract void registerCapabilities();
	public abstract List<IEventHandler> getEventHandlers();
	public abstract List<IGuiHandler> getGuiHandlers();
	public abstract void registerPackets();
	
	protected abstract Class<? extends IModItems> getModItemClass();
	protected abstract Object getModInstance();
	
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
    
    @SuppressWarnings("deprecation")
	public void registerEventHandlers() {
		getEventHandlers().forEach((IEventHandler handler) -> {
			switch (handler.getBusType()) {
			case DEFAULT:
			case FORGE:
				MinecraftForge.EVENT_BUS.register(handler);
				break;
			case FML:
		    	FMLCommonHandler.instance().bus().register(handler);
				break;
			default:
				Log.error("Can not register event handler: " + handler.getClass().getName());
				break;
			}
		});
	}

	protected void registerGuiHandlers() {
		for (IGuiHandler guiHandler : getGuiHandlers()) {
			NetworkRegistry.INSTANCE.registerGuiHandler(getModInstance(), guiHandler);
		}
	}
}
