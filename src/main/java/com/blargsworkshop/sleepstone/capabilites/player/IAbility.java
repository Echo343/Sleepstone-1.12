package com.blargsworkshop.sleepstone.capabilites.player;

import java.util.Map;

import com.blargsworkshop.sleepstone.powers.Power;

import net.minecraft.entity.player.EntityPlayer;

public interface IAbility {
	
	public void init(EntityPlayer player);
	
	public Map<Power, Boolean> getAbilityMap();
	public boolean getAbility(Power ability);
	public void setAbility(Power ability, boolean flag);
	public void setAbilityWithoutSync(Power ability, boolean flag);
	
	public String getBondedStoneId();
	public void setBondedStoneId(String bondedStoneId);
	public void setBondedStoneIdWithoutSync(String bondedStoneId);
	
	public void syncAll();

	public boolean isAbilityAvailable(Power ability);
}
