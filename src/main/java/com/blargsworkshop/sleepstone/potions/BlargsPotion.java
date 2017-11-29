package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BlargsPotion extends Potion {
		
	public BlargsPotion(ResourceLocation registryName, String messageKey) {
		super(false, 0);
		this.setRegistryName(registryName);
		this.setPotionName(messageKey);
		setIconIndex(5, 1);
		Log.detail("Registering BlargsPotion(" + registryName.toString() + ") for " + messageKey);
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