package com.blargsworkshop.sleepstone.stones;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.Gui.GuiEnum;
import com.blargsworkshop.sleepstone.SleepstoneMod.DEBUG;
import com.blargsworkshop.sleepstone.network.BasicMessage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemSleepstone extends Item implements IMessageHandler<BasicMessage, IMessage> {
	
	private static final String TEXT_SLEEPSTONE_SUFFERING_EFFECTS_OF_WARPING = "text.sleepstone.suffering_effects_of_warping";
	private static final String TEXT_SLEEPSTONE_BED_DESTROYED = "text.sleepstone.bed_destroyed";
	private static final int WARP_SICKNESS_DURATION = 20*60*10;
	private static final String SOUND_TELEPORT = SleepstoneMod.MODID + ":" + "Teleport";
	private static final String SOUND_SWOOSH = SleepstoneMod.MODID + ":" + "Swoosh";
	private static final String BASIC_SLEEPSTONE_NAME = "basicsleepstone";
	private static final String TEXTURE_SLEEPSTONE = "sleepstonemod:sleepy";

	public ItemSleepstone() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(BASIC_SLEEPSTONE_NAME);
		this.setTextureName(TEXTURE_SLEEPSTONE);
		this.setCreativeTab(SleepstoneMod.tabSleepstone);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			player.openGui(SleepstoneMod.instance, GuiEnum.STONE.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
//		player.setItemInUse(item, getMaxItemUseDuration(item));
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
	
	protected void warpPlayerToBed(EntityPlayer player, World world) {		
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
				SleepstoneMod.debug("Warping to: " + (coord.posX + 0.5) + ", " + (coord.posY + 0.1) + ", " + (coord.posZ + 0.5), DEBUG.CASUAL, player);
			}
			else {
				player.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization(TEXT_SLEEPSTONE_BED_DESTROYED)));
			}
		}
		else {
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 100;
	}

	@Override
	public IMessage onMessage(BasicMessage message, MessageContext ctx) {
		//I believe this is the player/client the message came from.
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		if (player.isPotionActive(NovelPotion.warpSickness.id)) {
			player.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization(TEXT_SLEEPSTONE_SUFFERING_EFFECTS_OF_WARPING)));
		}
		else {
			this.warpPlayerToBed(player, player.worldObj);
		}
		return null;
	}
	
//	@Override
//	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
//		return EnumAction.block;
//	}

}
