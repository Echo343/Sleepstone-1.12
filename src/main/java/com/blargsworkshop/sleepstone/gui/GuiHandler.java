package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.sleepstone.items.stone.StoneContainerItem;
import com.blargsworkshop.sleepstone.items.stone.StoneInventoryItem;
import com.blargsworkshop.sleepstone.items.stone.Guis.GuiStone;
import com.blargsworkshop.sleepstone.items.stone.Guis.GuiStoneInventory;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneContainerItem(player, player.inventory, new StoneInventoryItem(player.getHeldItem()));
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			return new GuiStone(player);
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new GuiStoneInventory(new StoneContainerItem(player, player.inventory, new StoneInventoryItem(player.getHeldItem())));
		}
		else {
			return null;
		}
	}

}
