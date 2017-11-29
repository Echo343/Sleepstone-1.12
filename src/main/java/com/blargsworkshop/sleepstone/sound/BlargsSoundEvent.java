package com.blargsworkshop.sleepstone.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class BlargsSoundEvent extends SoundEvent{

	public BlargsSoundEvent(ResourceLocation name) {
		super(name);
		this.setRegistryName(name);
	}

}
