package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.potion.potioneffects.ChronowalkPotionEffect;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickEventHandler implements IEventHandler {
	
	private static final int CHECK_RATE = 50;
	private static final int REFRESH_DURATION = CHECK_RATE + 30;
	
	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent e) {
		if (e.phase != Phase.END || e.player.ticksExisted % CHECK_RATE != 0) {
			return;
		}
		
		// Pathfinder
		if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Slots.Pathfinder)) {
			e.player.addPotionEffect(new BlargsPotionEffect(Potions.foodSaturation, REFRESH_DURATION, 0, true, true));
		}
		
		// Time & Space
		if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Slots.TimeSpace)) {
			e.player.addPotionEffect(new ChronowalkPotionEffect(REFRESH_DURATION));
		}
	}
}
