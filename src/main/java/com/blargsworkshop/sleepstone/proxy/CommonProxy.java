package com.blargsworkshop.sleepstone.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.engine.network.PacketDispatcher;
import com.blargsworkshop.engine.proxy.BlargsCommonProxy;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatus;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusStorage;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;
import com.blargsworkshop.sleepstone.events.TickEventHandler;
import com.blargsworkshop.sleepstone.events.MainEventHandler;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerPropMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.OpenGuiMessage;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy extends BlargsCommonProxy {
	
	@Override
	public void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IAbilityStatus.class, new AbilityStatusStorage(), AbilityStatus.class);
	}

	@Override
	public Map<EventHandlerType, IEventHandler> getEventHandlers() {
		Map<EventHandlerType, IEventHandler> handlers = new HashMap<>();
		handlers.put(EventHandlerType.FORGE, new MainEventHandler());
		handlers.put(EventHandlerType.FML, new TickEventHandler());
		return handlers;
	}
	
	@Override
	public List<IGuiHandler> getGuiHandlers() {
		List<IGuiHandler> guiHandlers = new ArrayList<>();
		guiHandlers.add(new GuiHandler());
		return guiHandlers;
	}

	@Override
	public void registerPackets() {
		PacketDispatcher dispatcher = NetworkOverlord.register(ModInfo.ID);
		
		//Messages handled on the server
		dispatcher.registerMessage(OpenGuiMessage.class);
		dispatcher.registerMessage(CommandMessage.class);
		
		//Bidirectional Messages
		dispatcher.registerMessage(SyncAllPlayerPropsMessage.class);
		dispatcher.registerMessage(SyncPlayerPropMessage.class);
	}

	@Override
	protected Class<? extends IModItems> getModItemClass() {
		return ModItems.class;
	}

	@Override
	protected Object getModInstance() {
		return SleepstoneMod.getInstance();
	}
}
