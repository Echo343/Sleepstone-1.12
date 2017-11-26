package com.blargsworkshop.sleepstone.capabilites;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SleepstonePlayerPropsProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(ISleepstonePlayerProps.class)
	public static final Capability<ISleepstonePlayerProps> SLEEPSTONE_PLAYER_PROPS = null;
	
	private ISleepstonePlayerProps instance;

	public SleepstonePlayerPropsProvider() {
		instance = SLEEPSTONE_PLAYER_PROPS.getDefaultInstance();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == SLEEPSTONE_PLAYER_PROPS;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == SLEEPSTONE_PLAYER_PROPS ? SLEEPSTONE_PLAYER_PROPS.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return SLEEPSTONE_PLAYER_PROPS.getStorage().writeNBT(SLEEPSTONE_PLAYER_PROPS, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		SLEEPSTONE_PLAYER_PROPS.getStorage().readNBT(SLEEPSTONE_PLAYER_PROPS, instance, null, nbt);
	}

}
