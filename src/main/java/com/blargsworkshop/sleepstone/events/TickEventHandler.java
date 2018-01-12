package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.powers.HorseSpirit;
import com.blargsworkshop.sleepstone.powers.Power;

import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class TickEventHandler implements IEventHandler {
	
	private static final int CHECK_RATE = 5;
	private static final int REFRESH_DURATION = CHECK_RATE + 1;
	
	@SubscribeEvent
	public void onPlayerTickEvent(PlayerTickEvent e) {
		if (e.phase != Phase.END) {
			return;
		}
		
		if (Utils.isClient(e.player.getEntityWorld())) {
			IAttributeInstance stepHeightAttribute = e.player.getEntityAttribute(HorseSpirit.STEP_HEIGHT);
			if (stepHeightAttribute != null && e.player.stepHeight != stepHeightAttribute.getAttributeValue()) {
				e.player.stepHeight = (float) stepHeightAttribute.getAttributeValue();
			}
		}
		
		if (Utils.isServer(e.player.getEntityWorld()) && e.player.ticksExisted % CHECK_RATE == 0) {
			// Pathfinder
			if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Power.IRON_STOMACH)) {
				e.player.addPotionEffect(new BlargsPotionEffect(Potions.foodSaturation, REFRESH_DURATION, 0, true, true));
			}
			
			// Time & Space
			if (e.player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null).isAbilityAvailable(Power.WINDWALKER)) {
				e.player.addPotionEffect(new BlargsPotionEffect(Potions.horseSpirit, REFRESH_DURATION, 0, true, true));
			}
		}
	}
}
