package com.blargsworkshop.sleepstone.utility;

import java.util.HashSet;
import java.util.Set;

import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	public static Set<Class<? extends Gem>> getUniqueGemTypes() {
		Set<Class<? extends Gem>> gemTypeSet = new HashSet<Class<? extends Gem>>(Slots.values().length, 1f);
		for (Slots gem : Slots.values()) {
			gemTypeSet.add(gem.getGemType());
		}
		return gemTypeSet;
	}
}
