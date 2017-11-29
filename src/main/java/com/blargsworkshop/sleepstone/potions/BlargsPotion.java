package com.blargsworkshop.sleepstone.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BlargsPotion extends Potion {
	
//	public BlargsPotion(int potionId, String messageKey) {
//		super(potionId, false, 0);
//		setPotionName(messageKey);
//		// TODO use custom icon
//		setIconIndex(5, 1);
//		Log.detail("Registering BlargsPotion: " + potionId + " for " + messageKey);
//	}
	
	public BlargsPotion(ResourceLocation registryName) {
		super(false, 0);
		this.setRegistryName(registryName);
	}

	/**
	 * Override this method to add effects.
	 */
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {	}
	
	/**
	 * Override this to enable performEffect to be called.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		return false;
	}
}