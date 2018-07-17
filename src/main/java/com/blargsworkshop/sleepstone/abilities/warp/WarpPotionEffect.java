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

public class WarpPotionEffect extends BlargsPotionEffect {
	public enum WarpType {
		WARP,
		HELLJUMP
	}
	
	private final BlockPos startLocation;
	private final WarpType warpType;

	public WarpPotionEffect(@Nonnull EntityPlayer player, WarpType type) {
		super(Potions.warpChannel, getChannelDuration(type), 0, false, true);
		this.startLocation = player.getPosition();
		this.warpType = type;
	}
	
	public BlockPos getStartLocation() {
		return this.startLocation;
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {
		if (Utils.isServer(entity.getEntityWorld()) && entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			switch (warpType) {
				case WARP:
					Warp.INSTANCE.warpPlayerToBed(player);
					break;
				default:
					break;
			}
		}
	}
	
	private static int getChannelDuration(WarpType type) {
		if (Log.compare(LogLevel.DEBUG)) {
			return 5;
		}
		else {
			switch (type) {
				case WARP:
				case HELLJUMP:
				default:
					return 20 * 4;
			}
		}
	}
}
