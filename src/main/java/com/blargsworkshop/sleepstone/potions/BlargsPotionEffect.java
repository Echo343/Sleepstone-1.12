package com.blargsworkshop.sleepstone.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

public abstract class BlargsPotionEffect extends PotionEffect {

	public BlargsPotionEffect(PotionEffect potionEffect) {
		super(potionEffect);
		init();
	}

//	public BlargsPotionEffect(int potionId, int duration) {
//		super(potionId, duration);
//		init();
//	}
//
//	public BlargsPotionEffect(int potionId, int duration, int amplifier) {
//		super(potionId, duration, amplifier);
//		init();
//	}
//
//	public BlargsPotionEffect(int potionId, int duration, int amplifier, boolean isAmbient) {
//		super(potionId, duration, amplifier, isAmbient);
//		init();
//	}
	
	public BlargsPotionEffect(BlargsPotion potion, int enderwarpDuration) {
		super(potion);
	}

	private void init() {
		this.getCurativeItems().clear();		
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase entity) {
		// Potion Effect is about to end.  Do something cool.
		if (this.getDuration() == 1) {
//			onFinishedPotionEffect(entity);
		}
		return super.onUpdate(entity);
	}

	/**
	 * @param entity
	 */
//	protected abstract void onFinishedPotionEffect(EntityLivingBase entity);
//	{
//		Potion p = Potion.potionTypes[this.getPotionID()];
//		if (p instanceof BlargsPotion) {
//			((BlargsPotion) p).onFinishedPotion(entity, this.getDuration(), this.getAmplifier());
//		}
//	}

}
