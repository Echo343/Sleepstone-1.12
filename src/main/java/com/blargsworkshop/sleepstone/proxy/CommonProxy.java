package com.blargsworkshop.sleepstone.proxy;

import com.blargsworkshop.sleepstone.IModItems;
import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.capabilites.player.Ability;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStorage;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;
import com.blargsworkshop.sleepstone.events.MainEventHandler;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy extends BlargsCommonProxy {
	
	@Override
	public void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IAbility.class, new AbilityStorage(), Ability.class);
	}

	@Override
	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new MainEventHandler());
//    	FMLCommonHandler.instance().bus().register(new FMLEventHandler());
	}

	@Override
	public void registerGuiHandlers() {
		super.registerGuiHandlerHelper(SleepstoneMod.instance, new GuiHandler());
	}

	@Override
	public void registerPackets() {
		PacketDispatcher.registerPackets();
	}
	
	@Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
    	Log.detail("Retrieving player from CommonProxy for message on side " + ctx.side);
    	return ctx.getServerHandler().player;
    }

	@Override
	protected Class<? extends IModItems> getModItemClass() {
		return ModItems.class;
	}
}
