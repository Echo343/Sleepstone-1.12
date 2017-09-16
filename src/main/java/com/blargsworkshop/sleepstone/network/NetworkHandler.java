package com.blargsworkshop.sleepstone.network;

import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class NetworkHandler implements IMessageHandler<BasicMessage, IMessage> {
	public static SimpleNetworkWrapper networkWrapper = null;

	private EntityPlayerMP getPlayer(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
	
	private World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getEntityWorld();
	}
	
	@Override
	public IMessage onMessage(BasicMessage command, MessageContext ctx) {
		switch (command.getCommand()) {
		case WARP:
			warp(ctx);
			break;
		case OpenInvGui:
			openInventoryGui(ctx);
			break;
		default:
			break;
		}
		return null;
	}

	/** Command Functions */
	
	/**
	 * Attempts to warp the player to their bed.
	 */
	private void warp(MessageContext ctx) {
		EntityPlayerMP player = getPlayer(ctx);
		if (player.isPotionActive(NovelPotion.warpSickness.id)) {
			player.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("text.sleepstone.suffering_effects_of_warping")));
		}
		else {
			Sleepstone.warpPlayerToBed(player, getWorld(ctx));
		}
	}
	
	/**
	 * Opens the StoneInventoryGui from a client side gui.
	 */
	private void openInventoryGui(MessageContext ctx) {
		getPlayer(ctx).openGui(SleepstoneMod.instance, GuiEnum.STONE_INVENTORY.ordinal(), getWorld(ctx), 0, 0, 0);
	}
}