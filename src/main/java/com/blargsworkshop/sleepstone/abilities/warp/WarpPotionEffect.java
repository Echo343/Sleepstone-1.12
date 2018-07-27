package com.blargsworkshop.sleepstone.abilities.warp;

import javax.annotation.Nonnull;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.logger.Log.LogLevel;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

public class WarpPotionEffect extends BlargsPotionEffect implements IWarpEffect {
	private final static int WARP_CHANNEL_DURATION = Log.compare(LogLevel.ADV_DEBUG) ? 5 : 20 * 4;
	
	private final BlockPos startLocation;

	public WarpPotionEffect(@Nonnull EntityPlayer player) {
		super(Potions.warpChannel, WARP_CHANNEL_DURATION, 0, false, true);
		this.startLocation = player.getPosition();
	}
	
	@Override
	public BlockPos getStartLocation() {
		return this.startLocation;
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {
		if (Utils.isServer(entity.getEntityWorld()) && entity instanceof EntityPlayerMP) {
			Warp.INSTANCE.warpPlayerToBed((EntityPlayerMP) entity);
		}
	}	
}
