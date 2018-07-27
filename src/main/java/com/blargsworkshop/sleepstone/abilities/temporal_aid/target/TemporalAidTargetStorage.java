package com.blargsworkshop.sleepstone.abilities.temporal_aid.target;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TemporalAidTargetStorage implements IStorage<ITemporalAidTarget> {
		
	@Override
	public NBTBase writeNBT(Capability<ITemporalAidTarget> capability, ITemporalAidTarget instance,	EnumFacing side) {
		return instance.getTarget().serializeNBT();
	}

	@Override
	public void readNBT(Capability<ITemporalAidTarget> capability, ITemporalAidTarget instance, EnumFacing side, NBTBase nbt) {
	}

}
