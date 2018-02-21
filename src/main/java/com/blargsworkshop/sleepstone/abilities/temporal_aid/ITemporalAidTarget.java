package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import net.minecraft.entity.player.EntityPlayer;

public interface ITemporalAidTarget {
	
	public EntityPlayer getTarget();
	public void setTarget(EntityPlayer player);
	public void setTargetWithoutSyncing(EntityPlayer player);
	
}
