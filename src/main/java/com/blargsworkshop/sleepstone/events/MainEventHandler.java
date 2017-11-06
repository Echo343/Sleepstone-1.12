package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * EventHandler
 */
public class MainEventHandler {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.getEntity() instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.getEntity()) == null) {
			ExtendedPlayer.register((EntityPlayer) event.getEntity());
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld())) {
			if (event.getEntity() instanceof EntityPlayer) {
				ExtendedPlayer.get((EntityPlayer) event.getEntity()).syncAll();
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			NBTTagCompound compound = new NBTTagCompound();
			ExtendedPlayer.get(event.getOriginal()).saveNBTData(compound);
			ExtendedPlayer.get(event.getEntityPlayer()).loadNBTData(compound);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld()) && event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (event.getDistance() > 3.0F && Utils.isAbilityAvailable(player, Slots.Stone)) {
				event.setDistance(2.0F);
				Log.debug(player.getDisplayName() + " just fell on the server.", player);				
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingEntityDrops(LivingDropsEvent event) {
		if (!Utils.isServer(event.getEntity().getEntityWorld())) { return; }
		MobDrops.handleGlobalGemDropRates(event);
		MobDrops.handleFireGemDropRates(event);
		MobDrops.handleEtherealGemDropRates(event);
		MobDrops.handleEnderShardDropRates(event);
	}
}