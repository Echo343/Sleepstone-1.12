package com.blargsworkshop.sleepstone.capabilites.player;

import com.blargsworkshop.engine.logger.Log;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AbilityProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IAbility.class)
	public static final Capability<IAbility> ABILITY_CAPABILITY = null;
	
	private IAbility instance;

	public AbilityProvider(EntityPlayer player) {
		instance = ABILITY_CAPABILITY.getDefaultInstance();
		instance.init(player);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == ABILITY_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ABILITY_CAPABILITY) {
			Log.detail("getCapability - Sleepstone Ability");
		}
		return capability == ABILITY_CAPABILITY ? ABILITY_CAPABILITY.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ABILITY_CAPABILITY.getStorage().writeNBT(ABILITY_CAPABILITY, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ABILITY_CAPABILITY.getStorage().readNBT(ABILITY_CAPABILITY, instance, null, nbt);
	}

	public static IAbility getCapability(EntityPlayer player) {
		return player.getCapability(ABILITY_CAPABILITY, null);
	}
}
