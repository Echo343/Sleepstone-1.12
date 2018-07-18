package com.blargsworkshop.sleepstone.abilities.helljumper;

import javax.annotation.Nonnull;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.logger.Log.LogLevel;
import com.blargsworkshop.engine.potion.BlargsPotionEffect;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.abilities.warp.IWarpEffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

public class HelljumpWarpEffect extends BlargsPotionEffect implements IWarpEffect {
	private final static int WARP_CHANNEL_DURATION = Log.compare(LogLevel.DEBUG) ? 5 : 20 * 4;
	
	private final BlockPos startLocation;
	private final Helljump jumper;

	public HelljumpWarpEffect(@Nonnull Helljump jumper) {
		super(Potions.warpChannel, WARP_CHANNEL_DURATION, 0, false, true);
		this.jumper = jumper;
		this.startLocation = jumper.getPlayer().getPosition();
	}

	@Override
	public BlockPos getStartLocation() {
		return startLocation;
	}

	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {
		if (Utils.isServer(entity.getEntityWorld()) && entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			if (!jumper.tryJump()) {
				Utils.addChatMessage(player, "text.helljump.fizzle");
			}
		}
	}
}
