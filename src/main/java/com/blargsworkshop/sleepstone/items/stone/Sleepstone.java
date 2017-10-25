package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.items.BaseItem;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Sleepstone extends BaseItem {
	
	private static final int WARP_SICKNESS_DURATION = 20*60*10;
	private static final String SOUND_TELEPORT = ModInfo.ID + ":" + "Teleport";
	private static final String SOUND_SWOOSH = ModInfo.ID + ":" + "Swoosh";
	private static final String TEXTURE_SLEEPSTONE = ModInfo.ID + ":sleepy";
	
	protected boolean isReadyToUse = true;
	protected int coolDown = 0;

	public Sleepstone() {
		super("basicsleepstone", TEXTURE_SLEEPSTONE);
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (this.isReadyToUse) {
			if (!player.isSneaking()) {
//				player.openGui(SleepstoneMod.instance, GuiEnum.STONE.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
				if (Utils.isServer(player.worldObj)) {
//					player.travelToDimension(1);
					Utils.transferPlayerToDimension((EntityPlayerMP) player, 0, Vec3.createVectorHelper(100, 100, 100), 0.0);
				}
			}
			else {
				if (player.isPotionActive(NovelPotion.warpSickness.id)) {
					Utils.addChatMessage(player, "text.sleepstone.suffering_effects_of_warping");
					isReadyToUse = false;
				}
				else {
					// Warp after channeling
//					player.setItemInUse(item, item.getMaxItemUseDuration());
					if (Utils.isServer(player.worldObj)) {
//						player.travelToDimension(1);
						Utils.transferPlayerToDimension((EntityPlayerMP) player, 1, Vec3.createVectorHelper(100, 100, 100), 0.0);
					}
				}
			}
		}
		return item;
	}
		
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		if (Utils.isServer(player.worldObj)) {
			Log.debug("Ticks until warp: " + count, player);
		}
		if (count <= 1) {
			if (Utils.isServer(player.worldObj)) {
				warpPlayerToBed(player);
			}
			this.isReadyToUse = false;
		}
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.onUpdate(item, world, entity, p_77663_4_, p_77663_5_);
		if (isReadyToUse == false) {
			if (coolDown++ > 60) {
				isReadyToUse = true;
				coolDown = 0;
			}
		}
	}
	
	public static void warpPlayerToBed(EntityPlayer player) {
		World world = player.worldObj;
		if (Utils.isServer(world)) {
			ChunkCoordinates coord = player.getBedLocation(player.dimension);
			if (coord != null) {
				coord = EntityPlayer.verifyRespawnCoordinates(world, coord, false);
			}
			if (coord != null) {
				world.playSoundAtEntity(player, SOUND_SWOOSH, 1f, 1f);
				player.setPositionAndUpdate(coord.posX + 0.5, coord.posY + 0.1, coord.posZ + 0.5);
				PotionEffect warpSicknessEffect = new PotionEffect(NovelPotion.warpSickness.id, WARP_SICKNESS_DURATION);
				warpSicknessEffect.getCurativeItems().clear();
				player.addPotionEffect(warpSicknessEffect);
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
		return 20 * 4; // 4 seconds
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

}
