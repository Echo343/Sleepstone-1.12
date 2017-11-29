package com.blargsworkshop.sleepstone.sound;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundManager {

	/**
	 * Plays a sound at the entity's position.
	 * This must be called from both the client and the server.
	 * @param entity
	 * @param sound
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, SoundEvent sound, float volume, float pitch) {
		entity.playSound(sound, volume, pitch);
	}
	
	/**
	 * Plays a sound at the entity's position.
	 * This must be called from both the client and the server.
	 * @param entity
	 * @param sound
	 * @param volume relative to 1.0
	 * @param pitch relative to 1.0
	 */
	public static void playSoundAtEntity(Entity entity, SoundEvent sound) {
		playSoundAtEntity(entity, sound, 1f, 1f);
	}
	
	/**
	 * Plays a sound at the entity's position.
	 * Use this method when only calling from the server.
	 * @param entity
	 * @param sound
	 */
	public static void playSoundAtEntityFromServer(Entity entity, SoundEvent sound) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			player.getEntityWorld().playSound(null, new BlockPos(player), sound, player.getSoundCategory(), 1f, 1f);
		}
		else {
			playSoundAtEntity(entity, sound);
		}
	}

}
