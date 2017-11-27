package com.blargsworkshop.sleepstone.capabilites.player;

import java.util.Map;

import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.entity.player.EntityPlayer;

public interface IAbility {
	
	public void init(EntityPlayer player);
	
	public Map<Slots, Boolean> getAbilityMap();
	public boolean getAbility(Slots gem);
	public void setAbility(Slots gem, boolean flag);
	public void setAbilityWithoutSync(Slots gem, boolean flag);
	
	public String getBondedStoneId();
	public void setBondedStoneId(String bondedStoneId);
	public void setBondedStoneIdWithoutSync(String bondedStoneId);
	
	public void syncAll();

}
