package com.blargsworkshop.sleepstone.proxy;

import java.util.ArrayList;
import java.util.List;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.engine.network.PacketDispatcher;
import com.blargsworkshop.engine.proxy.BlargsCommonProxy;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.OpenAidGuiMessage;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.AidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.AidInventoryStorage;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.IAidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.ITemporalAidTarget;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.TemporalAidTarget;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.TemporalAidTargetStorage;
import com.blargsworkshop.sleepstone.events.AirMattressHandler;
import com.blargsworkshop.sleepstone.events.MainEventHandler;
import com.blargsworkshop.sleepstone.events.TickEventHandler;
import com.blargsworkshop.sleepstone.gui.GuiHandler;
import com.blargsworkshop.sleepstone.items.stone.properties.IStoneProperties;
import com.blargsworkshop.sleepstone.items.stone.properties.StoneProperties;
import com.blargsworkshop.sleepstone.items.stone.properties.StonePropertiesStorage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerBondedIdMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerPropMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.OpenGuiMessage;
import com.blargsworkshop.sleepstone.player.AbilityStatus;
import com.blargsworkshop.sleepstone.player.AbilityStatusStorage;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy extends BlargsCommonProxy {
	
	@Override
	public void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IAbilityStatus.class, new AbilityStatusStorage(), AbilityStatus::new);
		CapabilityManager.INSTANCE.register(IStoneProperties.class, new StonePropertiesStorage(), StoneProperties::new);
		CapabilityManager.INSTANCE.register(ITemporalAidTarget.class, new TemporalAidTargetStorage(), TemporalAidTarget::new);
		CapabilityManager.INSTANCE.register(IAidInventory.class, new AidInventoryStorage(), AidInventory::new);
	}

	@Override
	public List<IEventHandler> getEventHandlers() {
		List<IEventHandler> handlers = new ArrayList<>();
		handlers.add(new MainEventHandler());
		handlers.add(new TickEventHandler());
		handlers.add(new AirMattressHandler());
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
		dispatcher.registerMessage(OpenAidGuiMessage.class);
		
		//Bidirectional Messages
		dispatcher.registerMessage(SyncAllPlayerPropsMessage.class);
		dispatcher.registerMessage(SyncPlayerPropMessage.class);
		dispatcher.registerMessage(SyncPlayerBondedIdMessage.class);
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
