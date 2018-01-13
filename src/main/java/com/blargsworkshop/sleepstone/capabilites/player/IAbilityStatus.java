package com.blargsworkshop.sleepstone.capabilites.player;

import java.util.Map;

import com.blargsworkshop.sleepstone.abilities.Ability;

import net.minecraft.entity.player.EntityPlayer;

public interface IAbilityStatus {
	
	public void init(EntityPlayer player);
	
	public Map<Ability, Boolean> getAbilityMap();
	public boolean getAbility(Ability ability);
	public void setAbility(Ability ability, boolean flag);
	public void setAbilityWithoutSync(Ability ability, boolean flag);
	
	public String getBondedStoneId();
	public void setBondedStoneId(String bondedStoneId);
	public void setBondedStoneIdWithoutSync(String bondedStoneId);
	
	public void syncAll();

	public boolean isAbilityAvailable(Ability ability);
}
