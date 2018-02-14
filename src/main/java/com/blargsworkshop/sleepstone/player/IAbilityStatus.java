package com.blargsworkshop.sleepstone.player;

import java.util.Map;

import com.blargsworkshop.sleepstone.abilities.Ability;

public interface IAbilityStatus {
		
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
