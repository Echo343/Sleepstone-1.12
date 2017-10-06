package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * EventHandler
 */
public class MainEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
			ExtendedPlayer.register((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote) {
			if (event.entity instanceof EntityPlayer) {
				ExtendedPlayer.get((EntityPlayer) event.entity).syncAll();
			}
		}
	}
	
//	This is not needed right now, because you will need to lose your powers on death anyways.
//	@SubscribeEvent
//	public void onClonePlayer(PlayerEvent.Clone event) {
//		if (event.wasDeath) {
//			NBTTagCompound compound = new NBTTagCompound();
//			ExtendedPlayer.get(event.original).saveNBTData(compound);
//			ExtendedPlayer.get(event.entityPlayer).loadNBTData(compound);
//		}
//	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entity);
			if (event.distance > 3.0F && props.getNoFallDamage()) {
				event.distance = 2.0F;
			}
		}
	}
}