package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.IAidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.ITemporalAidTarget;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface TemporalAidProvider extends ICapabilityProvider {
	
	public static ITemporalAidTarget getTarget(EntityPlayer player) {
		return player.getCapability(PlayerTemporalAidProvider.getCapability(), null);
	}
	
	public static IAidInventory getInventory(ItemStack stone) {
		return stone.getCapability(StoneTemporalAidProvider.getCapability(), null);
	}
}
