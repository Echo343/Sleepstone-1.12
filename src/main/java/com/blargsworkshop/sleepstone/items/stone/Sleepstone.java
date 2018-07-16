package com.blargsworkshop.sleepstone.items.stone;

import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.BaseModItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Sleepstone extends BaseModItem {
	
//	private static final int WARP_CHANNEL_DURATION = 20 * 4;
	private static final String UNLOCALIZED_NAME = "basicsleepstone";
	private static final String REGISTRY_NAME = "sleepstone";
	
//	protected CooldownTimer cooldownTimer = new CooldownTimer();
	
	public Sleepstone() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME);
		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			player.openGui(SleepstoneMod.getInstance(), GuiEnum.ABILITY.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//			if (cooldownTimer.isItemReadyToUse(player)) {
//				if (!player.isSneaking()) {
//					player.openGui(SleepstoneMod.getInstance(), GuiEnum.ABILITY.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//				}
//				else {
////					player.openGui(SleepstoneMod.getInstance(), GuiEnum.STONE_INVENTORY.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//					if (player.isPotionActive(Potions.warpSickness)) {
//						Utils.addChatMessage(player, "text.sleepstone.suffering_effects_of_warping");
//						cooldownTimer.startCooldown(player);
//					}
//					else {
//						// Start channeling for warp.
//						player.setActiveHand(hand);
//					}
//				}
//			}
		}
		return super.onItemRightClick(world, player, hand);
	}
		
//	@Override
//	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
//		if (!(living instanceof EntityPlayer)) { return; }
//		EntityPlayer player = (EntityPlayer) living;
//		if (Utils.isServer(player.getEntityWorld())) {
//			Log.debug("Ticks until warp: " + count, player);
//		}
//		if (count <= 1) {
//			warpPlayerToBed(player);
//			cooldownTimer.startCooldown(player);
//		}
//	}
	
//	@Override
//	public void onUpdate(ItemStack item, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
//		super.onUpdate(item, world, entity, p_77663_4_, p_77663_5_);
//		if (entity instanceof EntityPlayer) {
//			EntityPlayer player = (EntityPlayer) entity;
//			if (!cooldownTimer.isItemReadyToUse(player)) {
//				cooldownTimer.nextTick(player);
//			}
//		}
//	}
	
	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		// return any value greater than zero
//		return WARP_CHANNEL_DURATION;
		return 1;
	}
	
//	private class CooldownTimer {
//		private Map<String, Integer> timers = new ConcurrentHashMap<>();
//			
//		public boolean isItemReadyToUse(EntityPlayer player) {
//			Integer value = timers.get(player.getDisplayNameString());
//			return (value != null && value > 0) ? false : true;
//		}
//		
//		public void startCooldown(EntityPlayer player) {
//			timers.put(player.getDisplayNameString(), 20 * 3); // ticks
//		}
//		
//		public void nextTick(EntityPlayer player) {
//			Integer count = timers.get(player.getDisplayNameString());
//			if (count != null && count > 0) {
//				timers.put(player.getDisplayNameString(), --count);
//			}
//		}
//	}
}
