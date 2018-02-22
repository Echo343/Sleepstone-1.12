package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import javax.annotation.Nonnull;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities.ITemporalAidTarget;

import net.minecraft.entity.player.EntityPlayer;

public class TemporalAidTarget implements ITemporalAidTarget {

	private EntityPlayer target = null;
	
	@Override
	public EntityPlayer getTarget() {
		return target;
	}

	@Override
	public void setTarget(@Nonnull EntityPlayer player) {
		target = player;
	}

}
