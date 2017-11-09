package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.Log.LogLevel;
import com.blargsworkshop.sleepstone.ModItems.Potions;

public class WarpSicknessPotionEffect extends BlargsPotionEffect {
	private static final int WARP_SICKNESS_DURATION = 
			(Log.Level == LogLevel.Debug || Log.Level == LogLevel.Detail) ? 20 * 10 : 20 * 60 * 12;

	public WarpSicknessPotionEffect() {
		super(Potions.warpSickness, WARP_SICKNESS_DURATION);
	}
	
//	@Override
//	protected void onFinishedPotionEffect(EntityLivingBase entity) {		
//		if (Utils.isServer(entity.worldObj) && entity instanceof EntityPlayer) {
//			Log.debug("Warp Sickness just ended.", (EntityPlayer) entity);
//		}
//	}
}
