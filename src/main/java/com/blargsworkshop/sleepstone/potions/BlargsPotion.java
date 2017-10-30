package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class BlargsPotion extends Potion {
	
	public BlargsPotion(int potionId, String messageKey) {
		super(potionId, false, 0);
		setPotionName(messageKey);
		// TODO use custom icon
		setIconIndex(5, 1);
		Log.detail("Registering BlargsPotion: " + potionId + " for " + messageKey);
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