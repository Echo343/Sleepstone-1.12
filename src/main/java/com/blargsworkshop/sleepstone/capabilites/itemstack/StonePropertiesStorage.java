package com.blargsworkshop.sleepstone.capabilites.itemstack;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StonePropertiesStorage implements IStorage<IStoneProperties> {
	
	private static final String UNIQUE_ID = "uniqueid";
	
	@Override
	public NBTBase writeNBT(Capability<IStoneProperties> capability, IStoneProperties instance,	EnumFacing side) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setString(UNIQUE_ID, instance.getUniqueId());
		return properties;
	}

	@Override
	public void readNBT(Capability<IStoneProperties> capability, IStoneProperties instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound properties = (NBTTagCompound) nbt;
		instance.setUniqueId(properties.getString(UNIQUE_ID));
	}

}
