package com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class TemporalAidProvider implements ICapabilityProvider {
	
	@CapabilityInject(ITemporalAidTarget.class)
	private static final Capability<ITemporalAidTarget> TEMPORAL_AID_CAPABILITY = null;
	
	private ITemporalAidTarget instance;

	public TemporalAidProvider() {
		instance = new TemporalAidTarget();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TEMPORAL_AID_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == TEMPORAL_AID_CAPABILITY ? TEMPORAL_AID_CAPABILITY.<T> cast(this.instance) : null;
	}

	public static ITemporalAidTarget getCapability(EntityPlayer player) {
		return player.getCapability(TEMPORAL_AID_CAPABILITY, null);
	}
}
