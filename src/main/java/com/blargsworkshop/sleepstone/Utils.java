package com.blargsworkshop.sleepstone;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

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
}
