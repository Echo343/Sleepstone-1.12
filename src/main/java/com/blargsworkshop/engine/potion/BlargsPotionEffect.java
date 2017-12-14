package com.blargsworkshop.engine.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class BlargsPotionEffect extends PotionEffect {

	public BlargsPotionEffect(PotionEffect potionEffect) {
		super(potionEffect);
		init();
	}

	public BlargsPotionEffect(Potion potion, int duration) {
		super(potion, duration);
		init();
	}

	public BlargsPotionEffect(Potion potion, int duration, int amplifier) {
		super(potion, duration, amplifier);
		init();
	}

	public BlargsPotionEffect(Potion potion, int duration, int amplifier, boolean isAmbient, boolean showParticles) {
		super(potion, duration, amplifier, isAmbient, showParticles);
		init();
	}
	
	private void init() {
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
	 * Override this to do something when the PotionEffect ends
	 * @param entity
	 */
	protected void onFinishedPotionEffect(EntityLivingBase entity) { }

}
