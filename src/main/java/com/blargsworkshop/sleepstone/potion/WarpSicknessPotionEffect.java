package com.blargsworkshop.sleepstone.potion;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.logger.Log.LogLevel;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class WarpSicknessPotionEffect extends BlargsPotionEffect {
	private static final int WARP_SICKNESS_DURATION = Log.compare(LogLevel.DEBUG) ? 20 * 10 : 20 * 60 * 12;

	public WarpSicknessPotionEffect() {
		super(Potions.warpSickness, WARP_SICKNESS_DURATION, 0, false, false);
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {		
		if (Utils.isServer(entity.getEntityWorld()) && entity instanceof EntityPlayer) {
			Log.debug("Warp Sickness just ended.", (EntityPlayer) entity);
		}
	}
}
