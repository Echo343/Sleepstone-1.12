package com.blargsworkshop.sleepstone;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class NovelPotion extends Potion {
	
	public static NovelPotion warpSickness;

	protected NovelPotion(int potionId, boolean isBadEffect, int liquidColor) {
		super(potionId, isBadEffect, liquidColor);
		Log.detail("Registering NovelPotion: " + potionId);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int p_76394_2_) {
		if (this.id == warpSickness.id) {
			//Warp Sickness effect here
		}
		else {
			super.performEffect(entity, p_76394_2_);
		}
	}
	
	@Override
	public Potion setIconIndex(int p_76399_1_, int p_76399_2_) {
		return super.setIconIndex(p_76399_1_, p_76399_2_);
	}

}
