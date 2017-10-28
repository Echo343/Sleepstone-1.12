package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class WarpSicknessPotion extends BlargsPotion {
	
	public WarpSicknessPotion(int potionId, boolean isBadEffect, int liquidColor) {
		super(potionId, isBadEffect, liquidColor);
		setPotionName("potion.warpingsickness");
		// TODO use custom icon
		setIconIndex(5, 1);
	}
	
	@Override
	public void onFinishedPotion(EntityLivingBase entity, int duration, int amplifier) {
		if (Utils.isServer(entity.worldObj) && entity instanceof EntityPlayer) {
			Log.debug("Warp Sickness just ended.", (EntityPlayer) entity); 
		}
	}
}
