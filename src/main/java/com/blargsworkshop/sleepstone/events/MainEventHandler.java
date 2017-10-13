package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.Utils;
import com.blargsworkshop.sleepstone.ModInfo.DEBUG;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
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
	public void onItemTossEvent(ItemTossEvent event) {
		if (event.entityItem.getEntityItem().getItem() == ModItems.itemSleepstone) {
			ExtendedPlayer extPlayer = ExtendedPlayer.get(event.player);
			if (event.player.isClientWorld()) {
				SleepstoneMod.debug(event.player.getDisplayName() + " just dropped a Sleepstone from the client.", DEBUG.DETAIL, event.player);				
			}
			else {
				SleepstoneMod.debug(event.player.getDisplayName() + " just dropped a Sleepstone from the server.", DEBUG.DETAIL, event.player);				
			}
			StoneInventory stone = new StoneInventory(event.entityItem.getEntityItem());
			if (extPlayer.getBondedStoneId() == stone.getUniqueId()) {
				extPlayer.setBondedStoneId(null, false);
				Utils.addChatMessage(event.player, "text.event.lost_attunment");
			}
		}
	}
	
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