package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.ModInfo.DEBUG;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy {
	
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
    	super.postInit(e);
    }
    
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
    	// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		SleepstoneMod.debug("Retrieving player from ClientProxy for message on side " + ctx.side, DEBUG.CASUAL);
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
    }
    
//    /**
//     * req. 1.8+ only
//     * @param ctx
//     * @return
//     */
//    @Override
//    public IThreadListener getThreadFromContext(MessageContext ctx) {
//        return (ctx.side.isClient() ? mc : super.getThreadFromContext(ctx));
//    }
    
}
