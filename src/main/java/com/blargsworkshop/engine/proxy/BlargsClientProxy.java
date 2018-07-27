package com.blargsworkshop.engine.proxy;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.event.RegisterModels;
import com.blargsworkshop.engine.logger.Log;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BlargsClientProxy implements IProxy {
	
	private IProxy commonProxy;
	
	protected abstract Class<? extends BlargsCommonProxy> getCommonProxyClass();
	protected abstract Class<? extends IModItems> getModItemClass();
	
	protected IProxy getCommonProxy() {
		if (commonProxy == null) {
			try {
				commonProxy = getCommonProxyClass().newInstance();
			} catch (InstantiationException |IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return commonProxy;
	}

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		getCommonProxy().preInit(e);
		Log.advDebug("BlargsClientProxy - PreInit");
		MinecraftForge.EVENT_BUS.register(new RegisterModels(getModItemClass()));
	}

	@Override
	public void init(FMLInitializationEvent e) {
		getCommonProxy().init(e);
		Log.advDebug("BlargsClientProxy - Init");
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		getCommonProxy().postInit(e);
		Log.advDebug("BlargsClientProxy - PostInit");
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		Log.advDebug("Retrieving player from ClientProxy for message on side " + ctx.side);
		return (ctx.side.isClient() ? Minecraft.getMinecraft().player : getCommonProxy().getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft() : getCommonProxy().getThreadFromContext(ctx));
	}

}
