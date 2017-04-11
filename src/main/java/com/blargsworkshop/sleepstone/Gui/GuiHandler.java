package com.blargsworkshop.sleepstone.Gui;

import com.blargsworkshop.sleepstone.Gui.Guis.GuiStone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (ID == GuiEnum.STONE.ordinal()) {
			return new GuiStone(player);
		}
		else {
			return null;
		}
	}

}
