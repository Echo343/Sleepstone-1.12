package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.Log.LogLevel;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.potions.EnderShardPotionEffect;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter.Dimension;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnderShard extends BaseItem {
	private static final int ENDERWARP_CHANNEL_DURATION = (Log.Level == LogLevel.Debug || Log.Level == LogLevel.Detail) ? 20 * 4 : 20 * 10;
	private static final String UNLOCALIZEDNAME = "endershard";
	private static final String TEXTURE = ModInfo.ID + ":endershard";
	
	public EnderShard() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (!player.isPotionActive(Potions.enderShardWarp.id) && player.dimension != Dimension.End.getValue()) {
			// Start channeling for ender warp.
			player.setItemInUse(item, item.getMaxItemUseDuration());
		}
		return item;
	}
	
	@Override
	public void onUsingTick(ItemStack item, EntityPlayer player, int count) {
		if (Utils.isServer(player.worldObj)) {
			Log.debug("Ticks until ender warp: " + count, player);
		}
		if (count <= 1) {
			if (Utils.isServer(player.worldObj) && player instanceof EntityPlayerMP) {
				warpPlayerToEnd((EntityPlayerMP) player, item);
			}
		}
	}
	
	public static void warpPlayerToEnd(EntityPlayerMP player, ItemStack item) {
		player.inventory.consumeInventoryItem(ModItems.itemEnderShard);
		player.addPotionEffect(new EnderShardPotionEffect(player));
		player.travelToDimension(Dimension.End.getValue());
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return ENDERWARP_CHANNEL_DURATION;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}
}
