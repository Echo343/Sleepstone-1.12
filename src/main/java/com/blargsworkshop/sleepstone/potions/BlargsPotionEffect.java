package com.blargsworkshop.sleepstone.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class BlargsPotionEffect extends PotionEffect {

	public BlargsPotionEffect(PotionEffect potionEffect) {
		super(potionEffect);
		this.getCurativeItems().clear();
	}

	public BlargsPotionEffect(int potionId, int duration) {
		super(potionId, duration);
		this.getCurativeItems().clear();
	}

	public BlargsPotionEffect(int potionId, int duration, int amplifier) {
		super(potionId, duration, amplifier);
		this.getCurativeItems().clear();
	}

	public BlargsPotionEffect(int potionId, int duration, int amplifier, boolean isAmbient) {
		super(potionId, duration, amplifier, isAmbient);
		this.getCurativeItems().clear();
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase entity) {
		// Potion Effect is about to end.  Do something cool.
		if (this.getDuration() == 1) {
			onFinishedPotionEffect(entity);
		}
		return super.onUpdate(entity);
	}

	/**
	 * @param entity
	 */
	protected void onFinishedPotionEffect(EntityLivingBase entity) {
		Potion p = Potion.potionTypes[this.getPotionID()];
		if (p instanceof BlargsPotion) {
			((BlargsPotion) p).onFinishedPotion(entity, this.getDuration(), this.getAmplifier());
		}
	}

}
