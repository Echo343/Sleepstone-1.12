package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.capabilites.itemstack.IStoneProperties;
import com.blargsworkshop.sleepstone.capabilites.itemstack.StonePropertiesProvider;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;
import com.blargsworkshop.sleepstone.items.stone.container.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStone;
import com.blargsworkshop.sleepstone.items.stone.gui.GuiStoneInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			IStoneProperties stoneProps = StonePropertiesProvider.getProperties(player.getHeldItemMainhand());
			IAbilityStatus props = AbilityStatusProvider.getCapability(player);
			if (!stoneProps.getUniqueId().equals(props.getBondedStoneId())) {
				props.setBondedStoneId(stoneProps.getUniqueId());
				Utils.addChatMessage(player, "text.guistone.sleepstone_attunes_to_you");
			}
			return null;
		}
		if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneContainer(world, player, player.inventory, player.getHeldItemMainhand());
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.STONE.ordinal()) {
			return new GuiStone(player, player.getHeldItemMainhand());
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new GuiStoneInventory(new StoneContainer(world, player, player.inventory, player.getHeldItemMainhand()));
		}
		else {
			return null;
		}
	}
}
