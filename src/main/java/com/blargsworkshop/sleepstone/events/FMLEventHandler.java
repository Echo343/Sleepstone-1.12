package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FMLEventHandler implements IEventHandler {

	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent e) {
		if (e.phase == Phase.END) {
			if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Slots.Pathfinder)) {
				e.player.addPotionEffect(new BlargsPotionEffect(Potions.foodSaturation, 3));
			}
		}
	}
}
