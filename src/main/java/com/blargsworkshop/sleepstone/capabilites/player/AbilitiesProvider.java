package com.blargsworkshop.sleepstone.capabilites.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AbilitiesProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IAbilities.class)
	public static final Capability<IAbilities> ABILITIES = null;
	
	private IAbilities instance;

	public AbilitiesProvider(EntityPlayer player) {
		instance = ABILITIES.getDefaultInstance();
		instance.init(player);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ABILITIES;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == ABILITIES ? ABILITIES.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ABILITIES.getStorage().writeNBT(ABILITIES, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ABILITIES.getStorage().readNBT(ABILITIES, instance, null, nbt);
	}

}
