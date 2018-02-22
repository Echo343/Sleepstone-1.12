package com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.AidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.TemporalAidTarget;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class TemporalAidProvider implements ICapabilityProvider {
	
	@CapabilityInject(ITemporalAidTarget.class)
	private static final Capability<ITemporalAidTarget> TEMPORAL_AID_CAPABILITY = null;
	
	@CapabilityInject(IAidInventory.class)
	private static final Capability<IAidInventory> AID_INVENTORY_CAPABILITY = null;
	
	private ITemporalAidTarget instance = null;
	private IAidInventory inventory = null;

	public TemporalAidProvider() {
		instance = new TemporalAidTarget();
	}
	
	public TemporalAidProvider(ItemStack stone) {
		inventory = new AidInventory(stone);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (instance != null && capability == TEMPORAL_AID_CAPABILITY)
				|| (inventory != null && capability == AID_INVENTORY_CAPABILITY);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		if (instance != null && capability == TEMPORAL_AID_CAPABILITY) {
			return TEMPORAL_AID_CAPABILITY.<T> cast(instance);
		}
		else if (inventory != null && capability == AID_INVENTORY_CAPABILITY) {
			return AID_INVENTORY_CAPABILITY.<T> cast(inventory);
		}
		else {
			return null;
		}
	}

	public static ITemporalAidTarget getCapability(EntityPlayer player) {
		return player.getCapability(TEMPORAL_AID_CAPABILITY, null);
	}
	
	public static IAidInventory getInventory(ItemStack stone) {
		return stone.getCapability(AID_INVENTORY_CAPABILITY, null);
	}
}
