package com.blargsworkshop.sleepstone.abilities.warp;

import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class WarpChannelingPotion extends BlargsPotion {

	public WarpChannelingPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		setBeneficial();
	}

	/**
	 * Override this method to add effects.
	 */
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			PotionEffect effect = player.getActivePotionEffect(this);
			if (effect != null && effect instanceof IWarpEffect) {
				BlockPos channelLocation = ((IWarpEffect) effect).getStartLocation();
				if (!channelLocation.equals(player.getPosition())) {
					player.removeActivePotionEffect(this);
					Utils.addStatusMessage(player, "text.warp.channel.interrupt");
				}
			}
		}
	}
	
	/**
	 * Override this to enable performEffect to be called.
	 */
	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 10 == 0;
	}
}
