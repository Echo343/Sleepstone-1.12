package com.blargsworkshop.sleepstone.potion.potioneffects;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.potion.potions.ChronowalkPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ChronowalkPotionEffect extends BlargsPotionEffect {

	public ChronowalkPotionEffect(int duration) {
		super(Potions.chronowalker, duration, 0, true, true);
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (Utils.isClient(player.getEntityWorld())) {
				player.stepHeight = ChronowalkPotion.NORMAL_STEPHEIGHT;
			}
			else {
				Log.debug("Chronowalking just ended.", player);
			}
		}
	}
}
