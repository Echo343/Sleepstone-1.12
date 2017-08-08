package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.sleepstone.items.stone.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStone;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStoneInventory;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneContainer(player, player.inventory, new StoneInventory(player.getHeldItem()));
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
			return new GuiStoneInventory(new StoneContainer(player, player.inventory, new StoneInventory(player.getHeldItem())));
		}
		else {
			return null;
		}
	}

}
