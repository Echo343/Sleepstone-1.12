package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.abilities.windwalker.Windwalker;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

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
			IAttributeInstance stepHeightAttribute = e.player.getEntityAttribute(Windwalker.STEP_HEIGHT);
			if (stepHeightAttribute != null && e.player.stepHeight != stepHeightAttribute.getAttributeValue()) {
				e.player.stepHeight = (float) stepHeightAttribute.getAttributeValue();
			}
		}
		IAttributeInstance jumpMovementFactorAttribute = e.player.getEntityAttribute(Windwalker.JUMP_MOVEMENT_FACTOR);
		if (jumpMovementFactorAttribute != null && e.player.jumpMovementFactor != jumpMovementFactorAttribute.getAttributeValue()) {
			e.player.jumpMovementFactor = (float) jumpMovementFactorAttribute.getAttributeValue();
		}
				
		if (Utils.isServer(e.player.getEntityWorld()) && e.player.ticksExisted % CHECK_RATE == 0) {
			IAbilityStatus abilityStatus = AbilityStatusProvider.getAbilityStatus(e.player);
			if (abilityStatus.isAbilityAvailable(Ability.IRON_STOMACH)) {
				e.player.addPotionEffect(new BlargsPotionEffect(Potions.foodSaturation, REFRESH_DURATION, 0, true, true));
			}
			
			if (abilityStatus.isAbilityAvailable(Ability.WINDWALKER)) {
				e.player.addPotionEffect(new BlargsPotionEffect(Potions.windwalker, REFRESH_DURATION, 0, true, true));
			}
		}
	}

	@Override
	public EventHandlerType getBusType() {
		return EventHandlerType.FML;
	}
}
