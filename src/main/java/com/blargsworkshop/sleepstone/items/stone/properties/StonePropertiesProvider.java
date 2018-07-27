package com.blargsworkshop.sleepstone.items.stone.properties;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class StonePropertiesProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IStoneProperties.class)
	private static final Capability<IStoneProperties> STONE_PROPERTY_CAPABILITY = null;
	
	private IStoneProperties properties;

	public StonePropertiesProvider(ItemStack stone) {
		properties = new StoneProperties(stone);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == STONE_PROPERTY_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == STONE_PROPERTY_CAPABILITY ? STONE_PROPERTY_CAPABILITY.<T> cast(properties) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return STONE_PROPERTY_CAPABILITY.getStorage().writeNBT(STONE_PROPERTY_CAPABILITY, properties, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		STONE_PROPERTY_CAPABILITY.getStorage().readNBT(STONE_PROPERTY_CAPABILITY, properties, null, nbt);
	}

	public static IStoneProperties getProperties(ItemStack stack) {
		return stack.getCapability(STONE_PROPERTY_CAPABILITY, null);
	}
}
