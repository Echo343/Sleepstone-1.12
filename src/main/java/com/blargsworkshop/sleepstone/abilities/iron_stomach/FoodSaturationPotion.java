package com.blargsworkshop.sleepstone.abilities.iron_stomach;

import java.lang.reflect.Field;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;

public class FoodSaturationPotion extends BlargsPotion {

	private static final float foodSaturationModifier = 0.15f;
	private static final int updateDuration = 20 * 10;
	private static Field foodSaturationLevel = null;
	private static boolean hasError = false;

	public FoodSaturationPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		setBeneficial();
		if (foodSaturationLevel == null) {
			try {
				try {
					foodSaturationLevel = FoodStats.class.getDeclaredField("foodSaturationLevel");
				}
				catch (NoSuchFieldException e) {
					// grep -w './build/taskLogs/retroMapReplacedMain.log' -e "foodSaturationLevel" | awk -F: '/RENAME MAP/ {print $2}'
					foodSaturationLevel = FoodStats.class.getDeclaredField("field_75125_b");
				}
				foodSaturationLevel.setAccessible(true);
			} catch (NoSuchFieldException | SecurityException | NullPointerException e) {
				hasError = true;
				Log.error("FoodSaturationLevel Reflection has failed.  Iron Stomach ability will not work.");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		// ticksExisted is used bc this potion is applied at infinite duration.
		if (entity instanceof EntityPlayer && entity.ticksExisted % updateDuration == 0) {
			if (amplifier == 0) {
				amplifier = 1;
			}
			FoodStats foodStats = ((EntityPlayer) entity).getFoodStats();
			float level = Math.min(foodStats.getSaturationLevel() + foodSaturationModifier * amplifier, (float) foodStats.getFoodLevel());
			try {
				foodSaturationLevel.setFloat(foodStats, level);
				if (Utils.isServer(entity.getEntityWorld())) {
					Log.advDebug("Food Saturation: " + foodStats.getSaturationLevel(), (EntityPlayer) entity);
				}
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
				hasError = true;
				Log.error("FoodSaturationLevel Reflection has failed.  Iron Stomach ability will not work.", (EntityPlayer) entity);
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return hasError ? false : true;
	}
	
	@Override
	public boolean shouldRenderHUD(PotionEffect effect) { return false; }
	
	@Override
	public boolean shouldRender(PotionEffect effect) { return false; }

}
