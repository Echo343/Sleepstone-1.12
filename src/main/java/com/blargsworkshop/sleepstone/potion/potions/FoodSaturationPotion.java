package com.blargsworkshop.sleepstone.potion.potions;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.potion.BlargsPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class FoodSaturationPotion extends BlargsPotion {

	private static final float foodSaturationModifier = 0.15f;
	private static final int updateDuration = 20 * 10;
	private int tick = 0;

	public FoodSaturationPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer) {
			if (amplifier == 0) {
				amplifier = 1;
			}
			FoodStats foodStats = ((EntityPlayer) entity).getFoodStats();
			foodStats.setFoodSaturationLevel(Math.min(foodStats.getSaturationLevel() + foodSaturationModifier * amplifier, (float) foodStats.getFoodLevel()));
			Log.detail("Food Saturation: " + foodStats.getSaturationLevel(), (EntityPlayer) entity);
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		if (++tick % updateDuration == 0) {
			tick = 0;
			return true;
		}
		return false;
	}

}
