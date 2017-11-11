package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.Log.LogLevel;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.potions.EnderShardPotionEffect;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter.Dimension;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EnderShard extends BaseItem {
	private static final int ENDERWARP_CHANNEL_DURATION = (Log.Level == LogLevel.Debug || Log.Level == LogLevel.Detail) ? 20 * 4 : 20 * 10;
	private static final String UNLOCALIZED_NAME = "endershard";
	private static final String REGISTRY_NAME = "endershard";
	
	public EnderShard() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!player.isPotionActive(Potions.enderShardWarp) && player.dimension != Dimension.End.getValue()) {
			// Start channeling for ender warp.
			player.setActiveHand(hand);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void onUsingTick(ItemStack item, EntityLivingBase living, int count) {
		if (!(living instanceof EntityPlayer)) { return; }
		EntityPlayer player = (EntityPlayer) living;
		if (Utils.isServer(player.getEntityWorld())) {
			Log.debug("Ticks until ender warp: " + count, player);
		}
		if (count <= 1) {
			if (Utils.isServer(player.getEntityWorld()) && player instanceof EntityPlayerMP) {
				warpPlayerToEnd((EntityPlayerMP) player, item);
			}
		}
	}
	
	public static void warpPlayerToEnd(EntityPlayerMP player, ItemStack item) {
//		player.inventory.clearMatchingItems(ModItems.itemEnderShard, -1, 1, null);
		item.shrink(1);
		player.addPotionEffect(new EnderShardPotionEffect(player));
		player.changeDimension(Dimension.End.getValue());
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return ENDERWARP_CHANNEL_DURATION;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.BLOCK;
	}
}
