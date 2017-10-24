package com.blargsworkshop.sleepstone.utility;

import java.util.HashSet;
import java.util.Set;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Utils {

	public static String localize(String messageKey) {
		return LanguageRegistry.instance().getStringLocalization(messageKey);
	}

	public static void addChatMessage(EntityPlayer player, String messageKey) {
		player.addChatMessage(new ChatComponentText(localize(messageKey)));
	}

	public static void addUnlocalizedChatMessage(EntityPlayer player, String message) {
		player.addChatMessage(new ChatComponentText(message));
	}
	
	public static boolean isServer(World worldObj) {
		return !worldObj.isRemote;
	}
	
	public static boolean isClient(World worldObj) {
		return !isServer(worldObj);
	}

	public static boolean isAbilityAvailable(EntityPlayer player, Slots slot) {
		ExtendedPlayer props = ExtendedPlayer.get(player);
		boolean doesPlayer = false;
		boolean hasStone = false;
		boolean hasGems = false;
		
		doesPlayer = props.getAbility(slot);
		
		//TODO search through in priority order
		ItemStack[] playerInv = player.inventory.mainInventory;
		ItemStack backupStone = null;
		for (int i = 0; i < playerInv.length; i++) {
			ItemStack indexedItemStack = playerInv[i];
			if (indexedItemStack != null && indexedItemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
				backupStone = backupStone == null ? indexedItemStack : backupStone;
				StoneInventory sInv = new StoneInventory(indexedItemStack);
				if (sInv.getUniqueId().equals(props.getBondedStoneId())) {
					hasStone = true;
					hasGems = sInv.hasGemInSlot(slot);
					break;
				}
			}
		}
		
		// Make this the new stone if the old one couldn't be found.
		if (hasStone == false && backupStone != null) {
			StoneInventory stoneInv = new StoneInventory(backupStone);
			props.setBondedStoneId(stoneInv.getUniqueId());
			hasStone = true;
			hasGems = stoneInv.hasGemInSlot(slot);
		}
		
		if (!doesPlayer) Log.info(slot.name() + " is turned off by the player", player);
		if (!hasStone) Log.info("Attuned sleepstone was not found in inventory", player);
		if (hasStone && !hasGems) Log.info("The sleepstone lacks the neccessary gem(s): " + slot.name(), player);
    	
		return doesPlayer && hasStone && hasGems;
	}

	public static Set<Item> getUniqueGems() {
		Set<Item> gemSet = new HashSet<Item>(Slots.values().length, 1f);
		for (Slots gem : Slots.values()) {
			gemSet.add(gem.getItem());
		}
		return gemSet;
	}
	
	public static void teleportPlayerToDimension(EntityPlayer player, int dimension) {
		if (Utils.isClient(player.worldObj)) { return; }
		
		int oldDimension = player.worldObj.provider.dimensionId;
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		MinecraftServer server = playerMP.mcServer;
		WorldServer worldServer = server.worldServerForDimension(dimension);
		ChunkCoordinates chunkcoordinates = worldServer.getSpawnPoint();
		player.addExperienceLevel(0);
		
		SimpleTeleporter teleporter = new SimpleTeleporter(worldServer, chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
		playerMP.mcServer.getConfigurationManager().transferPlayerToDimension(playerMP, dimension, teleporter);
		player.setPositionAndUpdate(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
		if (oldDimension == 1) {
			// For some reason teleporting out of the End does weird things.
			player.setPositionAndUpdate(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ);
			worldServer.spawnEntityInWorld(player);
			worldServer.updateEntityWithOptionalForce(player, false);
		}
		
	}
}
