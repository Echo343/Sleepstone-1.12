package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModInfo.DEBUG;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.Utils;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

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
	public void onItemTossEvent(ItemTossEvent event) {
		Utils.addUnlocalizedChatMessage(event.player, "Toss Event");
		if (isServer(event.player) && event.entityItem.getEntityItem().getItem() == ModItems.itemSleepstone) {
			ExtendedPlayer extPlayer = ExtendedPlayer.get(event.player);
			StoneInventory stone = new StoneInventory(event.entityItem.getEntityItem());
			SleepstoneMod.debug(event.player.getDisplayName() + " just dropped a Sleepstone from the server.", DEBUG.DETAIL, event.player);				
			if (extPlayer.getBondedStoneId().equals(stone.getUniqueId())) {
				extPlayer.unattune();
				Utils.addChatMessage(event.player, "text.event.lost_attunment");
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (isServer(event.entity.worldObj) && event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entity);
			if (event.distance > 3.0F && props.getNoFallDamage()) {
				event.distance = 2.0F;
				SleepstoneMod.debug(player.getDisplayName() + " just fell on the server.", DEBUG.DETAIL, player);				
			}
		}
	}
	
	private static boolean isServer(World worldObj) {
		return !worldObj.isRemote;
	}
	
	private static boolean isServer(EntityPlayer player) {
		return isServer(player.worldObj);
	}
}