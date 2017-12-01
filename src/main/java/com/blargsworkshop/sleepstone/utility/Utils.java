package com.blargsworkshop.sleepstone.utility;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * General helper functions.  Ideally I would like to get rid of this class.
 * But I'm not sure where to put the isServer, isClient methods.
 */
public class Utils {

	@SideOnly(Side.CLIENT)
	public static String localize(String messageKey, Object... parameters) {
		return I18n.format(messageKey, parameters);
	}

	public static void addChatMessage(EntityPlayer player, String messageKey, Object... args) {
		player.sendMessage(new TextComponentTranslation(messageKey, args));
	}

	public static void addUnlocalizedChatMessage(EntityPlayer player, String message) {
		player.sendMessage(new TextComponentString(message));
	}
	
	public static boolean isServer(World worldObj) {
		return !worldObj.isRemote;
	}
	
	public static boolean isClient(World worldObj) {
		return !isServer(worldObj);
	}
}
