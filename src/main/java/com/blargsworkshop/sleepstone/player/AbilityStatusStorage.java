package com.blargsworkshop.sleepstone.player;

import com.blargsworkshop.sleepstone.abilities.Ability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AbilityStatusStorage implements IStorage<IAbilityStatus> {
	
	private static final String BONDED_ID = "bondedstoneid";
	
	@Override
	public NBTBase writeNBT(Capability<IAbilityStatus> capability, IAbilityStatus instance,	EnumFacing side) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setString(BONDED_ID, instance.getBondedStoneId());
		instance.getAbilityMap().forEach((Ability ability, Boolean value) -> {
			properties.setBoolean(ability.name(), value);
		});
		return properties;
	}

	@Override
	public void readNBT(Capability<IAbilityStatus> capability, IAbilityStatus instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound properties = (NBTTagCompound) nbt;
		instance.setBondedStoneIdWithoutSync(properties.getString(BONDED_ID));
		instance.getAbilityMap().forEach((Ability ability, Boolean value) -> {
			instance.getAbilityMap().put(ability, properties.getBoolean(ability.name()));
		});
	}

}
