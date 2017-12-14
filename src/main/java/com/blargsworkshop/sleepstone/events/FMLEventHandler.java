package com.blargsworkshop.sleepstone.events;

import java.util.concurrent.atomic.AtomicInteger;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FMLEventHandler implements IEventHandler {
	
	private static AtomicInteger tick = new AtomicInteger();

	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent e) {
		if (e.phase != Phase.END || tick.addAndGet(1) % 50 != 0) {
			return;
		}
		
		tick.set(0);
		if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Slots.Pathfinder)) {
			e.player.addPotionEffect(new BlargsPotionEffect(Potions.foodSaturation, 3 * 20));
		}
	}
}
