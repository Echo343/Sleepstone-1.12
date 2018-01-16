package com.blargsworkshop.sleepstone.abilities;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class FoodSaturationPotion extends BlargsPotion {

	private static final float foodSaturationModifier = 0.15f;
	private static final int updateDuration = 20 * 10;

	public FoodSaturationPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		setBeneficial();
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		// ticksExisted is used bc this potion is applied at infinite duration.
		if (entity instanceof EntityPlayer && entity.ticksExisted % updateDuration == 0) {
			if (amplifier == 0) {
				amplifier = 1;
			}
			FoodStats foodStats = ((EntityPlayer) entity).getFoodStats();
			foodStats.setFoodSaturationLevel(Math.min(foodStats.getSaturationLevel() + foodSaturationModifier * amplifier, (float) foodStats.getFoodLevel()));
			if (Utils.isServer(entity.getEntityWorld())) {
				Log.detail("Food Saturation: " + foodStats.getSaturationLevel(), (EntityPlayer) entity);
			}
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
