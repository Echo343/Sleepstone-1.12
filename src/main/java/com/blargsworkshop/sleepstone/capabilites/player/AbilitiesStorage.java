package com.blargsworkshop.sleepstone.capabilites.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AbilitiesStorage implements IStorage<IAbilities> {

	public AbilitiesStorage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTBase writeNBT(Capability<IAbilities> capability, IAbilities instance,
			EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readNBT(Capability<IAbilities> capability, IAbilities instance, EnumFacing side,
			NBTBase nbt) {
		// TODO Auto-generated method stub

	}

}
