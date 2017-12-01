package com.blargsworkshop.sleepstone.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * A SoundEvent that also sets the registry name on construction.
 * <br>
 * You can use a normal SoundEvent if you remember to set the RegistryName.
 */
public class BlargsSoundEvent extends SoundEvent{

	public BlargsSoundEvent(ResourceLocation name) {
		super(name);
		this.setRegistryName(name);
	}

}
