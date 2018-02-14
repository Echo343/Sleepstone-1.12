package com.blargsworkshop.sleepstone.player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.engine.network.PacketDispatcher;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventory;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventoryProvider;
import com.blargsworkshop.sleepstone.items.stone.properties.IStoneProperties;
import com.blargsworkshop.sleepstone.items.stone.properties.StonePropertiesProvider;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerBondedIdMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerPropMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class AbilityStatus implements IAbilityStatus {
	
	private PacketDispatcher dispatcher = null;
	
	private String bondedStoneId = "";
	private EntityPlayer player;
	private EnumMap<Ability, Boolean> abilities = new EnumMap<Ability, Boolean>(Ability.class);

	public AbilityStatus(EntityPlayer player) {
		dispatcher = NetworkOverlord.get(ModInfo.ID);
		this.player = player;
		for (Ability ability : Ability.values()) {
			abilities.put(ability, false);
		}
	}
	
	@Override
	public Map<Ability, Boolean> getAbilityMap() {
		return abilities;
	}

	@Override
	public boolean getAbility(Ability ability) {
		Boolean bool = abilities.get(ability);
		return bool != null ? bool : false;
	}

	@Override
	public void setAbility(Ability ability, boolean value) {
		setAbility(ability, value, true);
	}

	@Override
	public void setAbilityWithoutSync(Ability ability, boolean value) {
		setAbility(ability, value, false);
	}

	@Override
	public String getBondedStoneId() {
		return bondedStoneId;
	}

	@Override
	public void setBondedStoneId(String bondedStoneId) {
		setBondedStoneId(bondedStoneId, true);
	}

	@Override
	public void setBondedStoneIdWithoutSync(String bondedStoneId) {
		setBondedStoneId(bondedStoneId, false);
	}

	@Override
	public void syncAll() {
		if (Utils.isServer(player.getEntityWorld())) {
			dispatcher.sendToPlayer(player, new SyncAllPlayerPropsMessage(player));
		}
		else {
			dispatcher.sendToServer(new SyncAllPlayerPropsMessage(player));
		}
	}
	
	protected void setAbility(Ability ability, boolean value, boolean doSync) {
		if (Boolean.valueOf(value).equals(abilities.get(ability))) {
			return;
		}
		abilities.put(ability, value);
		if (doSync) {
			if (Utils.isClient(player.getEntityWorld())) {
				dispatcher.sendToServer(new SyncPlayerPropMessage(ability, value));
				Log.debug("Setting " + ability + " to " + value + " on client and syncing to server", this.player);
			}
			else {
				dispatcher.sendToPlayer((EntityPlayerMP) player, new SyncPlayerPropMessage(ability, value));
				Log.debug("Setting " + ability + " to " + value + " on server and syncing to client", this.player);
			}
		}
	}
	
	protected void setBondedStoneId(String bondId, boolean doSync) {
		bondId = bondId == null ? "" : bondId.trim();
		if (this.bondedStoneId == bondId) {
			return;
		}
		this.bondedStoneId = bondId;
		if (doSync) {
			if (Utils.isClient(player.getEntityWorld())) {
				dispatcher.sendToServer(new SyncPlayerBondedIdMessage(bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on client", this.player);
			}
			else {
				dispatcher.sendToPlayer(player, new SyncPlayerBondedIdMessage(bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on server", this.player);
			}
		}
	}
	
	public boolean isAbilityAvailable(Ability ability) {
		boolean doesPlayer = false;
		boolean hasStone = false;
		boolean hasGems = false;
		
		doesPlayer = getAbility(ability);
		
		if (doesPlayer) {
			//TODO search through in priority order
			List<ItemStack> playerInv = player.inventory.mainInventory;
			ItemStack backupStone = null;
			for (ItemStack itemStack : playerInv) {
				if (itemStack != null && itemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
					backupStone = backupStone == null ? itemStack : backupStone;
					IStoneProperties stoneProps = StonePropertiesProvider.getProperties(itemStack);
					StoneInventory inventory = StoneInventoryProvider.getStoneInventory(itemStack);
					if (stoneProps.getUniqueId().equals(getBondedStoneId())) {
						hasStone = true;
						hasGems = inventory.hasGemInSlot(ability);
						break;
					}
				}
			}
			
			// Make this the new stone if the old one couldn't be found.
			if (hasStone == false && backupStone != null) {
				IStoneProperties stoneProps = StonePropertiesProvider.getProperties(backupStone);
				StoneInventory stoneInventory = StoneInventoryProvider.getStoneInventory(backupStone);
				setBondedStoneId(stoneProps.getUniqueId());
				hasStone = true;
				hasGems = stoneInventory.hasGemInSlot(ability);
			}
		}
		
//		if (!doesPlayer) Log.info(slot.name() + " is turned off by the player", player);
//		if (!hasStone) Log.info("Attuned sleepstone was not found in inventory", player);
//		if (hasStone && !hasGems) Log.info("The sleepstone lacks the neccessary gem(s): " + slot.name(), player);
    	
		return doesPlayer && hasStone && hasGems;
	}

}
