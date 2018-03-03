package com.blargsworkshop.sleepstone.abilities.temporal_aid.target;

import net.minecraft.entity.player.EntityPlayer;

public interface ITemporalAidTarget {
	
	public EntityPlayer getTarget();
	public void setTarget(EntityPlayer player);
	
}
