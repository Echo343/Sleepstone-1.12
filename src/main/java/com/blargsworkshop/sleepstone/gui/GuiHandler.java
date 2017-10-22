package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStone;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStoneInventory;
import com.blargsworkshop.sleepstone.utility.Utils;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			StoneInventory sInv = new StoneInventory(player.getHeldItem());
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if (!sInv.getUniqueId().equals(props.getBondedStoneId())) {
				props.setBondedStoneId(sInv.getUniqueId());
				Utils.addChatMessage(player, "text.guistone.sleepstone_attunes_to_you");
			}
			return null;
		}
		if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneContainer(world, player, player.inventory, new StoneInventory(player.getHeldItem()));
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			return new GuiStone(player, new StoneInventory(player.getHeldItem()));
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new GuiStoneInventory(new StoneContainer(world, player, player.inventory, new StoneInventory(player.getHeldItem())));
		}
		else {
			return null;
		}
	}
}
