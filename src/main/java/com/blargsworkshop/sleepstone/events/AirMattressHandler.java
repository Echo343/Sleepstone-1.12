package com.blargsworkshop.sleepstone.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AirMattressHandler implements IEventHandler {
	private static final Map<String, Boolean> playerWakeUpMap = new ConcurrentHashMap<>();
	private static final Map<String, BlockPos> spawnPoints = new ConcurrentHashMap<>();

	@SubscribeEvent
	public void onPlayerWakingUp(PlayerWakeUpEvent event) {
		if (Utils.isServer(event.getEntityPlayer().getEntityWorld()) && event.shouldSetSpawn()) {
			EntityPlayer player = event.getEntityPlayer();
			BlockPos oldSpawnPoint = player.getBedLocation();
			putOldSpawnPoint(player.getDisplayNameString(), oldSpawnPoint);
			playerWakeUpMap.put(player.getDisplayNameString(), Boolean.TRUE);
		}
	}
	
	@SubscribeEvent
	public void onSetPlayerSpawn(PlayerSetSpawnEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (Utils.isServer(player.getEntityWorld()) && didPlayerJustWakeUp(player.getDisplayNameString())) {
			playerWakeUpMap.put(player.getDisplayNameString(), Boolean.FALSE);
			BlockPos sleptInBedLocation = player.getBedLocation();
			if (sleptInBedLocation != null) {
				Block bedType = player.getEntityWorld().getBlockState(sleptInBedLocation).getBlock();
				if (bedType != null && bedType == ModItems.Blocks.airMattress) {
					BlockPos newSpawnPoint = player.getBedLocation();
					BlockPos oldSpawnPoint = spawnPoints.get(player.getDisplayNameString());
					if (!newSpawnPoint.equals(oldSpawnPoint)) {
						player.setSpawnPoint(oldSpawnPoint, false);
					}
				}
			}
		}
	}
	
	private void putOldSpawnPoint(String playerDisplayNameString, BlockPos oldSpawnPoint) {
		if (oldSpawnPoint == null) {
			spawnPoints.remove(playerDisplayNameString);
		}
		else {
			spawnPoints.put(playerDisplayNameString, oldSpawnPoint);
		}
	}
	
	private boolean didPlayerJustWakeUp(String playerDisplayNameString) {
		return Boolean.TRUE.equals(playerWakeUpMap.get(playerDisplayNameString));
	}

}
