package com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities;

import net.minecraft.entity.player.EntityPlayer;

public interface ITemporalAidTarget {
	
	public EntityPlayer getTarget();
	public void setTarget(EntityPlayer player);
	
}
