package com.blargsworkshop.sleepstone.potion.potions;

import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.sleepstone.powers.HorseSpirit;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

public class HorseSpiritPotion extends BlargsPotion {

	public HorseSpiritPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.22499999403953552D, 2);
		registerPotionAttributeModifier(HorseSpirit.STEP_HEIGHT, "6C2398CE-4F44-48EB-A8C8-7A4F2162E99C", 0.4D, 0);
		setBeneficial();
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
