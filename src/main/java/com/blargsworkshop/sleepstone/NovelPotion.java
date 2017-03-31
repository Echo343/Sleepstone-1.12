package com.blargsworkshop.sleepstone;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class NovelPotion extends Potion {
	
	public static NovelPotion warpSickness;

	protected NovelPotion(int potionId, boolean isBadEffect, int liquidColor) {
		super(potionId, isBadEffect, liquidColor);
		SleepstoneMod.debug("Registering NovelPotion: " + potionId, 3, null);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int p_76394_2_) {
		if (this.id <= 31) {
			super.performEffect(entity, p_76394_2_);
		}
		else {
			if (this.id == warpSickness.id) {
				
			}
		}
	}
	
	@Override
	public Potion setIconIndex(int p_76399_1_, int p_76399_2_) {
		return super.setIconIndex(p_76399_1_, p_76399_2_);
	}

}
