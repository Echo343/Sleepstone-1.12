package com.blargsworkshop.sleepstone.Gui;

import com.blargsworkshop.sleepstone.Gui.Guis.GuiStone;
import com.blargsworkshop.sleepstone.stones.StoneContainerItem;
import com.blargsworkshop.sleepstone.stones.StoneInventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import com.blargsworkshop.sleepstone.stones.Guis.GuiStoneInventory;

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
