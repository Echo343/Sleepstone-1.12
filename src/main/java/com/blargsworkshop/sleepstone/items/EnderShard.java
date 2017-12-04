package com.blargsworkshop.sleepstone.items;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.Log.LogLevel;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.potions.EnderShardPotionEffect;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class EnderShard extends BaseItem {
	private static final int ENDERWARP_CHANNEL_DURATION = Log.compare(LogLevel.DEBUG) ? 20 * 4 : 20 * 10;
	private static final String UNLOCALIZED_NAME = "endershard";
	private static final String REGISTRY_NAME = "endershard";
	
	public EnderShard() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			if (!player.isPotionActive(Potions.enderShardWarp) && player.dimension != DimensionType.THE_END.getId()) {
				// Start channeling for ender warp.
				player.setActiveHand(hand);
			}
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
			warpPlayerToEnd(player, item);
		}
	}
	
	private void warpPlayerToEnd(EntityPlayer player, ItemStack item) {
		if (Utils.isServer(player.getEntityWorld())) {
			item.shrink(1);
			player.addPotionEffect(new EnderShardPotionEffect(player));
			player.changeDimension(DimensionType.THE_END.getId());
		}
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
