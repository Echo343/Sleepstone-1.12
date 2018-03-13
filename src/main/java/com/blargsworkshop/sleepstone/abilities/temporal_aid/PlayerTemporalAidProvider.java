package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.ITemporalAidTarget;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.TemporalAidTarget;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerTemporalAidProvider implements TemporalAidProvider {

	@CapabilityInject(ITemporalAidTarget.class)
	private static final Capability<ITemporalAidTarget> TEMPORAL_AID_CAPABILITY = null;
	
	private ITemporalAidTarget instance = null;
	
	public PlayerTemporalAidProvider() {
		instance = new TemporalAidTarget();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TEMPORAL_AID_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == TEMPORAL_AID_CAPABILITY) {
			return TEMPORAL_AID_CAPABILITY.<T> cast(instance);
		}
		else {
			return null;
		}
	}

	static Capability<ITemporalAidTarget> getCapability() {
		return TEMPORAL_AID_CAPABILITY;
	}

}
