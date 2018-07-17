package com.blargsworkshop.sleepstone.abilities.warp;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.sound.SoundManager;
import com.blargsworkshop.engine.utility.SimpleTeleporter;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.ModItems.Sounds;
import com.blargsworkshop.sleepstone.items.stone.WarpSicknessPotionEffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum Warp {
	INSTANCE;
	
	private void warpPlayerToBed(EntityPlayerMP player) {
		World world = player.getEntityWorld();
		if (Utils.isServer(world)) {
			BlockPos bedPos = player.getBedLocation();
			if (bedPos != null) {
				BlockPos bedSpawnPos = EntityPlayer.getBedSpawnLocation(world, bedPos, false);
				if (bedSpawnPos != null) {
					SoundManager.playSoundAtEntityFromServer(player, Sounds.swoosh);
					SimpleTeleporter.INSTANCE.teleportPlayerWithinDimension(player, bedSpawnPos, true);
					player.addPotionEffect(new WarpSicknessPotionEffect());
					SoundManager.playSoundAtEntityFromServer(player, Sounds.teleport);
					Log.debug("Warping to: " + (bedSpawnPos.getX()) + ", " + (bedSpawnPos.getY()) + ", " + (bedSpawnPos.getZ()), player);
				}
				else {
					Utils.addStatusMessage(player, "text.sleepstone.bed_destroyed");
				}
			}
			else {
				Utils.addStatusMessage(player, "text.sleepstone.no_spawn_point");
			}
		}
	}
	
	public void startWarp(EntityPlayerMP player) {
		if (player.isPotionActive(Potions.warpSickness)) {
			Utils.addStatusMessage(player, "text.sleepstone.suffering_effects_of_warping");
		}
		else {
			warpPlayerToBed(player);
		}
	}

}
