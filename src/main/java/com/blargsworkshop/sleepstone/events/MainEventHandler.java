package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.Utils;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
		if (isServer(event.entity.worldObj)) {
			if (event.entity instanceof EntityPlayer) {
				ExtendedPlayer.get((EntityPlayer) event.entity).syncAll();
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.wasDeath) {
			NBTTagCompound compound = new NBTTagCompound();
			ExtendedPlayer.get(event.original).saveNBTData(compound);
			ExtendedPlayer.get(event.entityPlayer).loadNBTData(compound);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (isServer(event.entity.worldObj) && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (event.distance > 3.0F && Utils.isPowerAvailable(player, Slots.Stone)) {
				event.distance = 2.0F;
				Log.debug(player.getDisplayName() + " just fell on the server.", player);				
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingEntityDrops(LivingDropsEvent event) {
		if (!isServer(event.entity.worldObj)) { return; }
		
		EntityLivingBase mob = event.entityLiving;
		if (	mob instanceof EntityMob ||
				mob instanceof EntitySlime && ((EntitySlime) mob).getSlimeSize() == 1 ||
				mob instanceof EntityGhast) {
			double dropChance = Math.random();
			// 0.5% drop chance
			if (dropChance <= 0.0005) {
				// Pick a random gem
				int gemIndex = (int) (Math.random() * Slots.values().length);
				ItemStack loot = new ItemStack(Slots.values()[gemIndex].getItem());
				event.drops.add(new EntityItem(mob.worldObj, mob.posX, mob.posY, mob.posZ, loot));
			}
		}
	}
	
	private static boolean isServer(World worldObj) {
		return !worldObj.isRemote;
	}
	
//	private static boolean isServer(EntityPlayer player) {
//		return isServer(player.worldObj);
//	}
}