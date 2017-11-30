package com.blargsworkshop.sleepstone.utility;

import java.util.HashSet;
import java.util.Set;

import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Utils {

	@SideOnly(Side.CLIENT)
	public static String localize(String messageKey, Object... parameters) {
		return I18n.format(messageKey, parameters);
	}

	public static void addChatMessage(EntityPlayer player, String messageKey) {
		// TODO Fix: from the server send custom commandMessage, on receiving client: localize and send as chat
		player.sendMessage(new TextComponentString(isServer(player.getEntityWorld()) ? messageKey : localize(messageKey)));
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
	
	/*
Old GuiContainer

if (iicon != null)
{
    GL11.glDisable(GL11.GL_LIGHTING);
    GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for this.
    this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
    this.drawTexturedModelRectFromIcon(i, j, iicon, 16, 16);
    GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
    GL11.glEnable(GL11.GL_LIGHTING);
    flag1 = true;
}

New GuiContainer line 280
if (textureatlassprite != null)
{
    GlStateManager.disableLighting();
    this.mc.getTextureManager().bindTexture(slotIn.getBackgroundLocation());
    this.drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
    GlStateManager.enableLighting();
    flag1 = true;
}
	 */
}
