package com.blargsworkshop.sleepstone.gui;

import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.AidInventoryContainer;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory.AidInventoryGui;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.target.PlayerSelectionGui;
import com.blargsworkshop.sleepstone.items.stone.gui.AbilityGui;
import com.blargsworkshop.sleepstone.items.stone.inventory.gui.StoneInventoryContainer;
import com.blargsworkshop.sleepstone.items.stone.inventory.gui.StoneInventoryGui;
import com.blargsworkshop.sleepstone.items.stone.properties.IStoneProperties;
import com.blargsworkshop.sleepstone.items.stone.properties.StonePropertiesProvider;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.ABILITY.ordinal()) {
			IStoneProperties stoneProps = StonePropertiesProvider.getProperties(player.getHeldItemMainhand());
			IAbilityStatus props = AbilityStatusProvider.getCapability(player);
			if (!stoneProps.getUniqueId().equals(props.getBondedStoneId())) {
				props.setBondedStoneId(stoneProps.getUniqueId());
				Utils.addChatMessage(player, "text.guistone.sleepstone_attunes_to_you");
			}
			return null;
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneInventoryContainer(world, player, player.inventory, player.getHeldItemMainhand());
		}
		else if (guiId == GuiEnum.TEMPORAL_AID_INVENTORY.ordinal()) {
			return new AidInventoryContainer(world, player, player.inventory, player.getHeldItemMainhand());
		}
		else {
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world,	int x, int y, int z) {
		if (guiId == GuiEnum.ABILITY.ordinal()) {
			return new AbilityGui(player, player.getHeldItemMainhand());
		}
		else if (guiId == GuiEnum.STONE_INVENTORY.ordinal()) {
			return new StoneInventoryGui(new StoneInventoryContainer(world, player, player.inventory, player.getHeldItemMainhand()));
		}
		else if (guiId == GuiEnum.TEMPORAL_AID_PLAYER_SELECTION.ordinal()) {
			return new PlayerSelectionGui(player);
		}
		else if (guiId == GuiEnum.TEMPORAL_AID_INVENTORY.ordinal()) {
			return new AidInventoryGui(new AidInventoryContainer(world, player, player.inventory, player.getHeldItemMainhand()));
		}
		else {
			return null;
		}
	}
}
