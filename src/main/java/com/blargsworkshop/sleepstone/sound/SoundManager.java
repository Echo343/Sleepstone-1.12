package com.blargsworkshop.sleepstone.sound;

import com.blargsworkshop.sleepstone.ModInfo;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundManager {

	public static class Sounds {
		public static final SoundEvent teleport = new SoundEvent(new ResourceLocation(ModInfo.ID, "teleport"));
		public static final SoundEvent swoosh = new SoundEvent(new ResourceLocation(ModInfo.ID, "swoosh"));
	}
	/**
	 * Plays a sound at the entity's position.
	 * @param entity
	 * @param soundPath ex: "sleepstonemod:Swoosh"
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, SoundEvent sound, float volume, float pitch) {
//		entity.playSoundAtEntity(entity, soundPath, volume, pitch);
		entity.playSound(sound, volume, pitch);
	}
	
	/**
	 * Plays a sound at the entity's position.
	 * @param entity
	 * @param soundPath ex: "sleepstonemod:Swoosh"
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, SoundEvent sound) {
		playSoundAtEntity(entity, sound, 1f, 1f);
	}

}
