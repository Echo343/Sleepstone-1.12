package com.blargsworkshop.sleepstone.capabilites;

import com.blargsworkshop.sleepstone.items.stone.Slots;

public interface ISleepstonePlayerProps {
	public static final String BONDED_ID = "BondedStoneId";
	
	public boolean getAbility(Slots gem);
	public void setAbility(Slots gem, boolean flag);
	public void setAbilityWithoutSync(Slots gem, boolean flag);
	
	public String getBondedStoneId();
	public void setBondedStoneId(String bondedStoneId);
	public void setBondedStoneIdWithoutSync(String bondedStoneId);
	
	public void syncAll();
}
