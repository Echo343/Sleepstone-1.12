package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TemporalAidTargetStorage implements IStorage<ITemporalAidTarget> {
		
	@Override
	public NBTBase writeNBT(Capability<ITemporalAidTarget> capability, ITemporalAidTarget instance,	EnumFacing side) {
		NBTTagCompound properties = new NBTTagCompound();
		
		return properties;
	}

	@Override
	public void readNBT(Capability<ITemporalAidTarget> capability, ITemporalAidTarget instance, EnumFacing side, NBTBase nbt) {
//		NBTTagCompound properties = (NBTTagCompound) nbt;
//		instance.setBondedStoneIdWithoutSync(properties.getString(BONDED_ID));
//		instance.getAbilityMap().forEach((Ability ability, Boolean value) -> {
//			instance.getAbilityMap().put(ability, properties.getBoolean(ability.name()));
//		});
	}

}
