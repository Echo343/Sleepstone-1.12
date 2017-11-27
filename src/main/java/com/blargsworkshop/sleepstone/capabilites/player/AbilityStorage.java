package com.blargsworkshop.sleepstone.capabilites.player;

import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AbilityStorage implements IStorage<IAbility> {
	
	private static final String BONDED_ID = "bondedstoneid";
	
	@Override
	public NBTBase writeNBT(Capability<IAbility> capability, IAbility instance,	EnumFacing side) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setString(BONDED_ID, instance.getBondedStoneId());
		instance.getAbilityMap().forEach((Slots ability, Boolean value) -> {
			properties.setBoolean(ability.name(), value);
		});
		return properties;
	}

	@Override
	public void readNBT(Capability<IAbility> capability, IAbility instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound properties = (NBTTagCompound) nbt;
		instance.setBondedStoneIdWithoutSync(properties.getString(BONDED_ID));
		instance.getAbilityMap().forEach((Slots ability, Boolean value) -> {
			instance.getAbilityMap().put(ability, properties.getBoolean(ability.name()));
		});
	}

}
