package com.blargsworkshop.sleepstone.utility;

import java.util.HashSet;
import java.util.Set;

import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Utils {

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

	public static boolean isAbilityAvailable(EntityPlayer player, Slots slot) {
//		ExtendedPlayer props = ExtendedPlayer.get(player);
//		boolean doesPlayer = false;
//		boolean hasStone = false;
//		boolean hasGems = false;
//		
//		doesPlayer = props.getAbility(slot);
//		
//		//TODO search through in priority order
//		List<ItemStack> playerInv = player.inventory.mainInventory;
//		ItemStack backupStone = null;
//		for (ItemStack itemStack : playerInv) {
//			if (itemStack != null && itemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
//				backupStone = backupStone == null ? itemStack : backupStone;
//				StoneInventory sInv = new StoneInventory(itemStack);
//				if (sInv.getUniqueId().equals(props.getBondedStoneId())) {
//					hasStone = true;
//					hasGems = sInv.hasGemInSlot(slot);
//					break;
//				}
//			}
//		}
//		
//		// Make this the new stone if the old one couldn't be found.
//		if (hasStone == false && backupStone != null) {
//			StoneInventory stoneInv = new StoneInventory(backupStone);
//			props.setBondedStoneId(stoneInv.getUniqueId());
//			hasStone = true;
//			hasGems = stoneInv.hasGemInSlot(slot);
//		}
//		
//		if (!doesPlayer) Log.info(slot.name() + " is turned off by the player", player);
//		if (!hasStone) Log.info("Attuned sleepstone was not found in inventory", player);
//		if (hasStone && !hasGems) Log.info("The sleepstone lacks the neccessary gem(s): " + slot.name(), player);
//    	
//		return doesPlayer && hasStone && hasGems;
		return false;
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
