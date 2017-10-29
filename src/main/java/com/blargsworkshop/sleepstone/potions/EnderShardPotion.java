package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;

public class EnderShardPotion extends BlargsPotion {

	public EnderShardPotion(int potionId, boolean isBadEffect, int liquidColor) {
		super(potionId, isBadEffect, liquidColor);
		setPotionName("potion.endershard");
		// TODO use custom icon
		setIconIndex(5, 1);
	}

	public void onFinishedPotion(EntityLivingBase entity, int duration, int amplifier, ChunkCoordinates position) {
		if (Utils.isServer(entity.worldObj) && entity instanceof EntityPlayer) {
			Log.debug("Ender Shard just ended.", (EntityPlayer) entity); 
		}
	}
}
