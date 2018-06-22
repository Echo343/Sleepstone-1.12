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

	@SubscribeEvent
	public void onPlayerWakingUp(PlayerWakeUpEvent event) {
		if (Utils.isServer(event.getEntityPlayer().getEntityWorld()) && event.shouldSetSpawn()) {
			setPlayerWakeUp(event.getEntityPlayer());
		}
	}
	
	@SubscribeEvent(receiveCanceled = true)
	public void onSetPlayerSpawn(PlayerSetSpawnEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (Utils.isServer(player.getEntityWorld()) && didPlayerJustWakeUp(player)) {
			clearPlayerWakeUp(player);
			if (!event.isCanceled()) {
				BlockPos sleptInBedLocation = event.getNewSpawn();
				if (sleptInBedLocation != null) {
					Block blockType = player.getEntityWorld().getBlockState(sleptInBedLocation).getBlock();
					if (blockType != null && blockType == ModItems.Blocks.airMattress && event.isCancelable()) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
	private boolean didPlayerJustWakeUp(EntityPlayer player) {
		return Boolean.TRUE.equals(playerWakeUpMap.get(player.getDisplayNameString()));
	}
	
	private void setPlayerWakeUp(EntityPlayer player) {
		playerWakeUpMap.put(player.getDisplayNameString(), Boolean.TRUE);
	}
	
	private void clearPlayerWakeUp(EntityPlayer player) {
		playerWakeUpMap.put(player.getDisplayNameString(), Boolean.FALSE);
	}
	
	@Override
	public EventHandlerType getBusType() {
		return EventHandlerType.FORGE;
	}

}
