package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.Log;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BlargsClientProxy implements IProxy {
	
	private IProxy commonProxy;
	
	protected abstract Class<? extends BlargsCommonProxy> getCommonProxyClass();
	
	protected IProxy getCommonProxy() {
		if (commonProxy == null) {
			try {
				commonProxy = getCommonProxyClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return commonProxy;
	}

//	public BlargsClientProxy(Class<? extends BlargsCommonProxy> commonProxyClass) {
//		try {
//			commonProxy = commonProxyClass.newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		getCommonProxy().preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		getCommonProxy().init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		getCommonProxy().postInit(e);
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		Log.detail("Retrieving player from ClientProxy for message on side " + ctx.side);
		return (ctx.side.isClient() ? Minecraft.getMinecraft().player : getCommonProxy().getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft() : getCommonProxy().getThreadFromContext(ctx));
	}

}
