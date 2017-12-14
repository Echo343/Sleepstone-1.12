package com.blargsworkshop.sleepstone.potion.potions;

import com.blargsworkshop.engine.potion.BlargsPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class FoodSaturationPotion extends BlargsPotion {

	private static final float foodSaturationModifier = 1;

	public FoodSaturationPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer) {
			FoodStats foodStats = ((EntityPlayer) entity).getFoodStats();
			foodStats.setFoodSaturationLevel(Math.min(foodStats.getSaturationLevel() + foodSaturationModifier * (float) amplifier, (float) foodStats.getFoodLevel()));
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		int k = 50 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        }
        else {
            return true;
        }
	}

}
