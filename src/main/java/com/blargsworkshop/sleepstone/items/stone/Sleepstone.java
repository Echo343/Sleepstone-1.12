package com.blargsworkshop.sleepstone.items.stone;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.BaseItem;
import com.blargsworkshop.sleepstone.potions.WarpSicknessPotionEffect;
import com.blargsworkshop.sleepstone.sound.SoundManager;
import com.blargsworkshop.sleepstone.sound.SoundManager.Sounds;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Sleepstone extends BaseItem {
	
	private static final int WARP_CHANNEL_DURATION = 20 * 4;
	private static final String UNLOCALIZED_NAME = "basicsleepstone";
	private static final String REGISTRY_NAME = "sleepstone";
	
	protected CooldownTimer cooldownTimer = new CooldownTimer();
	
	public Sleepstone() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME);
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (cooldownTimer.isItemReadyToUse(player)) {
			if (!player.isSneaking()) {
				player.openGui(SleepstoneMod.instance, GuiEnum.STONE.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
			else {
				if (player.isPotionActive(Potions.warpSickness)) {
					Utils.addChatMessage(player, "text.sleepstone.suffering_effects_of_warping");
					cooldownTimer.startCooldown(player);
				}
				else {
					// Start channeling for warp.
					player.setActiveHand(hand);
				}
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
		
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		if (!(living instanceof EntityPlayer)) { return; }
		EntityPlayer player = (EntityPlayer) living;
		if (Utils.isServer(player.getEntityWorld())) {
			Log.debug("Ticks until warp: " + count, player);
		}
		if (count <= 1) {
			if (Utils.isServer(player.getEntityWorld()) && player instanceof EntityPlayerMP) {
				warpPlayerToBed((EntityPlayerMP) player);
			}
			cooldownTimer.startCooldown(player);
		}
	}
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		super.onUpdate(item, world, entity, p_77663_4_, p_77663_5_);
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (!cooldownTimer.isItemReadyToUse(player)) {
				cooldownTimer.nextTick(player);
			}
		}
	}
	
	public static void warpPlayerToBed(EntityPlayerMP player) {
		World world = player.getEntityWorld();
		if (Utils.isServer(world)) {
			
			BlockPos bedPos = EntityPlayer.getBedSpawnLocation(world, player.getBedLocation(player.dimension), false);
			if (bedPos != null) {
				SoundManager.playSoundAtEntityFromServer(player, Sounds.swoosh);
				SimpleTeleporter.teleportPlayerWithinDimension(player, bedPos);
				player.addPotionEffect(new WarpSicknessPotionEffect());
				SoundManager.playSoundAtEntityFromServer(player, Sounds.teleport);
				Log.debug("Warping to: " + (bedPos.getX()) + ", " + (bedPos.getY()) + ", " + (bedPos.getZ()), player);
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
		return WARP_CHANNEL_DURATION;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.BLOCK;
	}
	
	private class CooldownTimer {
		private Map<String, Integer> timers = new ConcurrentHashMap<>();
			
		public boolean isItemReadyToUse(EntityPlayer player) {
			Integer value = timers.get(player.getDisplayNameString());
			return (value != null && value > 0) ? false : true;
		}
		
		public void startCooldown(EntityPlayer player) {
			timers.put(player.getDisplayNameString(), 20 * 3); // ticks
		}
		
		public void nextTick(EntityPlayer player) {
			Integer count = timers.get(player.getDisplayNameString());
			if (count != null && count > 0) {
				timers.put(player.getDisplayNameString(), --count);
			}
		}
	}
}
