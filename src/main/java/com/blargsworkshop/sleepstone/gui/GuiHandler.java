package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.sleepstone.items.stone.container.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStone;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStoneInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
//			StoneInventory sInv = new StoneInventory(player.getHeldItemMainhand());
//			ExtendedPlayer props = ExtendedPlayer.get(player);
//			if (!sInv.getUniqueId().equals(props.getBondedStoneId())) {
//				props.setBondedStoneId(sInv.getUniqueId());
//				Utils.addChatMessage(player, "text.guistone.sleepstone_attunes_to_you");
//			}
			return null;
		}
		if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneContainer(world, player, player.inventory, new StoneInventory(player.getHeldItemMainhand()));
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			return new GuiStone(player, new StoneInventory(player.getHeldItemMainhand()));
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new GuiStoneInventory(new StoneContainer(world, player, player.inventory, new StoneInventory(player.getHeldItemMainhand())));
		}
		else {
			return null;
		}
	}
}
