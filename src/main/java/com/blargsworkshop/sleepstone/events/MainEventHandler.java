package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * EventHandler
 */
public class MainEventHandler implements IEventHandler {
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.ID, "sleepstone_player_props"), new AbilityProvider((EntityPlayer) event.getObject()));
			Log.detail("Sleepstone Ability capability has been attached.");
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld())) {
			if (event.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getEntity();
				AbilityProvider.getCapability(player).syncAll();
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IStorage<IAbility> dataStore = AbilityProvider.ABILITY_CAPABILITY.getStorage();
			NBTBase data = dataStore.writeNBT(AbilityProvider.ABILITY_CAPABILITY, AbilityProvider.getCapability(event.getOriginal()), null);
			dataStore.readNBT(AbilityProvider.ABILITY_CAPABILITY, AbilityProvider.getCapability(event.getEntityPlayer()), null, data);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld()) && event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (event.getDistance() > 3.0F && AbilityProvider.getCapability(player).isAbilityAvailable(Slots.Stone)) {
				event.setDistance(2.0F);
				Log.debug(player.getDisplayNameString() + " just fell on the server.", player);				
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