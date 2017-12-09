package com.blargsworkshop.engine.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BlargsPotion extends Potion {
		
	public BlargsPotion(ResourceLocation registryName, String messageKey) {
		super(false, 0);
		this.setRegistryName(registryName);
		this.setPotionName(messageKey);
		// TODO change icon
		setIconIndex(5, 1);
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