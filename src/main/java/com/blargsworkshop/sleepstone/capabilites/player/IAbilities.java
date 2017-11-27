package com.blargsworkshop.sleepstone.capabilites.player;

import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.entity.player.EntityPlayer;

public interface IAbilities {
	public static final String BONDED_ID = "BondedStoneId";
	
	public void init(EntityPlayer player);
	
	public boolean getAbility(Slots gem);
	public void setAbility(Slots gem, boolean flag);
	public void setAbilityWithoutSync(Slots gem, boolean flag);
	
	public String getBondedStoneId();
	public void setBondedStoneId(String bondedStoneId);
	public void setBondedStoneIdWithoutSync(String bondedStoneId);
	
	public void syncAll();
}
