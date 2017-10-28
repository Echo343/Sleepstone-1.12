package com.blargsworkshop.sleepstone.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class BlargsPotionEffect extends PotionEffect {

	public BlargsPotionEffect(PotionEffect p_i1577_1_) {
		super(p_i1577_1_);
		// TODO Auto-generated constructor stub
	}

	public BlargsPotionEffect(int p_i1574_1_, int p_i1574_2_) {
		super(p_i1574_1_, p_i1574_2_);
		// TODO Auto-generated constructor stub
	}

	public BlargsPotionEffect(int p_i1575_1_, int p_i1575_2_, int p_i1575_3_) {
		super(p_i1575_1_, p_i1575_2_, p_i1575_3_);
		// TODO Auto-generated constructor stub
	}

	public BlargsPotionEffect(int p_i1576_1_, int p_i1576_2_, int p_i1576_3_, boolean p_i1576_4_) {
		super(p_i1576_1_, p_i1576_2_, p_i1576_3_, p_i1576_4_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase entity) {
		// Potion Effect is about to end.  Do something cool.
		if (this.getDuration() == 1) {
			Potion p = Potion.potionTypes[this.getPotionID()];
			if (p instanceof BlargsPotion) {
				((BlargsPotion) p).onFinishedPotion(entity, this.getDuration(), this.getAmplifier());
			}
		}
		return super.onUpdate(entity);
	}

}
