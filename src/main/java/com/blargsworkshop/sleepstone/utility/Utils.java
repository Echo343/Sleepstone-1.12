package com.blargsworkshop.sleepstone.utility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Utils {

	public static String localize(String messageKey) {
//		return LanguageRegistry.instance().getStringLocalization(messageKey);
		return messageKey;
	}

	public static void addChatMessage(EntityPlayer player, String messageKey) {
//		player.addChatMessage(new ChatComponentText(localize(messageKey)));
	}

	public static void addUnlocalizedChatMessage(EntityPlayer player, String message) {
//		player.addChatMessage(new ChatComponentText(message));
	}
	
	public static boolean isServer(World worldObj) {
		return !worldObj.isRemote;
	}
	
	public static boolean isClient(World worldObj) {
		return !isServer(worldObj);
	}

	public static boolean isAbilityAvailable(EntityPlayer player, Slots slot) {
//		ExtendedPlayer props = ExtendedPlayer.get(player);
//		boolean doesPlayer = false;
//		boolean hasStone = false;
//		boolean hasGems = false;
//		
//		doesPlayer = props.getAbility(slot);
//		
//		//TODO search through in priority order
//		List<ItemStack> playerInv = player.inventory.mainInventory;
//		ItemStack backupStone = null;
//		for (ItemStack itemStack : playerInv) {
//			if (itemStack != null && itemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
//				backupStone = backupStone == null ? itemStack : backupStone;
//				StoneInventory sInv = new StoneInventory(itemStack);
//				if (sInv.getUniqueId().equals(props.getBondedStoneId())) {
//					hasStone = true;
//					hasGems = sInv.hasGemInSlot(slot);
//					break;
//				}
//			}
//		}
//		
//		// Make this the new stone if the old one couldn't be found.
//		if (hasStone == false && backupStone != null) {
//			StoneInventory stoneInv = new StoneInventory(backupStone);
//			props.setBondedStoneId(stoneInv.getUniqueId());
//			hasStone = true;
//			hasGems = stoneInv.hasGemInSlot(slot);
//		}
//		
//		if (!doesPlayer) Log.info(slot.name() + " is turned off by the player", player);
//		if (!hasStone) Log.info("Attuned sleepstone was not found in inventory", player);
//		if (hasStone && !hasGems) Log.info("The sleepstone lacks the neccessary gem(s): " + slot.name(), player);
//    	
//		return doesPlayer && hasStone && hasGems;
		return false;
	}

	public static Set<Item> getUniqueGems() {
		Set<Item> gemSet = new HashSet<Item>(Slots.values().length, 1f);
		for (Slots gem : Slots.values()) {
			gemSet.add(gem.getItem());
		}
		return gemSet;
	}
}
