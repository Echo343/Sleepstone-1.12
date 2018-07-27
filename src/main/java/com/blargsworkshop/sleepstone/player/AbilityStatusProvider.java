package com.blargsworkshop.sleepstone.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AbilityStatusProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IAbilityStatus.class)
	private static final Capability<IAbilityStatus> ABILITY_STATUS_CAPABILITY = null;
	
	private IAbilityStatus instance;

	public AbilityStatusProvider(EntityPlayer player) {
		instance = new AbilityStatus(player);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ABILITY_STATUS_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == ABILITY_STATUS_CAPABILITY ? ABILITY_STATUS_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ABILITY_STATUS_CAPABILITY.getStorage().writeNBT(ABILITY_STATUS_CAPABILITY, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ABILITY_STATUS_CAPABILITY.getStorage().readNBT(ABILITY_STATUS_CAPABILITY, instance, null, nbt);
	}

	public static IAbilityStatus getAbilityStatus(EntityPlayer player) {
		return player.getCapability(ABILITY_STATUS_CAPABILITY, null);
	}
	
	public static Capability<IAbilityStatus> getCapability() {
		return ABILITY_STATUS_CAPABILITY;
	}
}
