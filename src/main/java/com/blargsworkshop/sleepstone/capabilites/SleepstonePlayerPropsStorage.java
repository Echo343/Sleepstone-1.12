package com.blargsworkshop.sleepstone.capabilites;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SleepstonePlayerPropsStorage implements IStorage<ISleepstonePlayerProps> {

	public SleepstonePlayerPropsStorage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTBase writeNBT(Capability<ISleepstonePlayerProps> capability, ISleepstonePlayerProps instance,
			EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readNBT(Capability<ISleepstonePlayerProps> capability, ISleepstonePlayerProps instance, EnumFacing side,
			NBTBase nbt) {
		// TODO Auto-generated method stub

	}

}
