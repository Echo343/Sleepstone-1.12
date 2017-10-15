package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.Utils;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.BaseItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class Sleepstone extends BaseItem {
	
	private static final int WARP_SICKNESS_DURATION = 20*60*10;
	private static final String SOUND_TELEPORT = ModInfo.ID + ":" + "Teleport";
	private static final String SOUND_SWOOSH = ModInfo.ID + ":" + "Swoosh";
	private static final String TEXTURE_SLEEPSTONE = ModInfo.ID + ":sleepy";

	public Sleepstone() {
		super("basicsleepstone", TEXTURE_SLEEPSTONE);
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (!player.isSneaking()) {
			player.openGui(SleepstoneMod.instance, GuiEnum.STONE.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		else {
//			TODO - Begin Warp
//			player.openGui(SleepstoneMod.instance, GuiEnum.STONE_INVENTORY.ordinal(), world, 0, 0, 0);
		}
		return item;
	}
		
//	@Override
//	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
//		if (!player.worldObj.isRemote) {
//			player.addChatMessage(new ChatComponentText("curcount: " + count));
//			if (count <= 1) {
//				ChunkCoordinates c = player.getBedLocation(player.dimension);
//				if (c != null) {
//					player.setPositionAndUpdate(c.posX, c.posY, c.posZ);
//				}
//				player.stopUsingItem();
//				player.addChatMessage(new ChatComponentText("warpcount: " + count));
//			}
//		}
//	}
	
	public static void warpPlayerToBed(EntityPlayer player, World world) {		
		if (!world.isRemote) {
			ChunkCoordinates coord = player.getBedLocation(player.dimension);
			if (coord != null) {
				coord = EntityPlayer.verifyRespawnCoordinates(world, coord, false);
			}
			if (coord != null) {
				world.playSoundAtEntity(player, SOUND_SWOOSH, 1f, 1f);
				player.setPositionAndUpdate(coord.posX + 0.5, coord.posY + 0.1, coord.posZ + 0.5);
				player.addPotionEffect(new PotionEffect(NovelPotion.warpSickness.id, WARP_SICKNESS_DURATION));
				world.playSoundAtEntity(player, SOUND_TELEPORT, 1f, 1f);
				Log.debug("Warping to: " + (coord.posX + 0.5) + ", " + (coord.posY + 0.1) + ", " + (coord.posZ + 0.5), player);
			}
			else {
				Utils.addChatMessage(player, "text.sleepstone.bed_destroyed");
			}
		}
	}
	
	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// return any value greater than zero
		return 1;
	}

//	@Override
//	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
//		return EnumAction.block;
//	}

}
