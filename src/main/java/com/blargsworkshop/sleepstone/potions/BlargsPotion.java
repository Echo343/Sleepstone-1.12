package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public abstract class BlargsPotion extends Potion {
	
	public BlargsPotion(int potionId, boolean isBadEffect, int liquidColor) {
		super(potionId, isBadEffect, liquidColor);
		Log.detail("Registering BlargsPotion: " + potionId);
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
	
	public void onFinishedPotion(EntityLivingBase entity, int duration, int amplifier) { }
}