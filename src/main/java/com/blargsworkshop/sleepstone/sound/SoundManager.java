package com.blargsworkshop.sleepstone.sound;

import net.minecraft.entity.Entity;

public class SoundManager {
	//TODO shrink this class to SoundUtil if it doesn't get much bigger in 12.2 update.

	/**
	 * Plays a sound at the entity's position.
	 * @param entity
	 * @param soundPath ex: "sleepstonemod:Swoosh"
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, String soundPath, float volume, float pitch) {
		entity.worldObj.playSoundAtEntity(entity, soundPath, volume, pitch);
	}
	
	/**
	 * Plays a sound at the entity's position.
	 * @param entity
	 * @param soundPath ex: "sleepstonemod:Swoosh"
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, String soundPath) {
		playSoundAtEntity(entity, soundPath, 1f, 1f);
	}

}
