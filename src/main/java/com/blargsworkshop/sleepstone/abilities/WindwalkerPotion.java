package com.blargsworkshop.sleepstone.abilities;

import com.blargsworkshop.engine.potion.BlargsPotion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

public class WindwalkerPotion extends BlargsPotion {

	public WindwalkerPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070634", 0.16D, 0);
		registerPotionAttributeModifier(Windwalker.STEP_HEIGHT, "6C2398CE-4F44-48EB-A8C8-7A4F2162E99C", 0.4D, 0);
		setBeneficial();
	}
}
