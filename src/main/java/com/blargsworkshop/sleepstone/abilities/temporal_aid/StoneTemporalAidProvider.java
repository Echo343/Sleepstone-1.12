package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.AidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.IAidInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class StoneTemporalAidProvider implements TemporalAidProvider {
	
	@CapabilityInject(IAidInventory.class)
	private static final Capability<IAidInventory> AID_INVENTORY_CAPABILITY = null;

	private IAidInventory inventory = null;
	
	public StoneTemporalAidProvider(ItemStack stone) {
		inventory = new AidInventory(stone);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == AID_INVENTORY_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == AID_INVENTORY_CAPABILITY) {
			return AID_INVENTORY_CAPABILITY.<T> cast(inventory);
		}
		else {
			return null;
		}
	}

	static Capability<IAidInventory> getCapability() {
		return AID_INVENTORY_CAPABILITY;
	}
}
