package com.blargsworkshop.sleepstone.capabilites.player;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.engine.network.PacketDispatcher;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerBondedIdMessage;
import com.blargsworkshop.sleepstone.network.packets.bidirectional.SyncPlayerPropMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class Ability implements IAbility {
	
	private PacketDispatcher dispatcher = null;
	
	private String bondedStoneId = "";
	private EntityPlayer player;
	private EnumMap<Slots, Boolean> abilities = new EnumMap<Slots, Boolean>(Slots.class);

	public void init(EntityPlayer player) {
		dispatcher = NetworkOverlord.get(ModInfo.ID);
		this.player = player;
		for (Slots slot : Slots.values()) {
			abilities.put(slot, false);
		}
	}
	
	@Override
	public Map<Slots, Boolean> getAbilityMap() {
		return abilities;
	}

	@Override
	public boolean getAbility(Slots gem) {
		Boolean bool = abilities.get(gem);
		return bool != null ? bool : false;
	}

	@Override
	public void setAbility(Slots gem, boolean value) {
		setAbility(gem, value, true);
	}

	@Override
	public void setAbilityWithoutSync(Slots gem, boolean value) {
		setAbility(gem, value, false);
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
	
	protected void setAbility(Slots gem, boolean value, boolean doSync) {
		if (Boolean.valueOf(value).equals(abilities.get(gem))) {
			return;
		}
		abilities.put(gem, value);
		if (doSync) {
			if (Utils.isClient(player.getEntityWorld())) {
				dispatcher.sendToServer(new SyncPlayerPropMessage(gem, value));
				Log.debug("Setting " + gem + " to " + value + " on client and syncing to server", this.player);
			}
			else {
				dispatcher.sendToPlayer((EntityPlayerMP) player, new SyncPlayerPropMessage(gem, value));
				Log.debug("Setting " + gem + " to " + value + " on server and syncing to client", this.player);
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
	
	public boolean isAbilityAvailable(Slots slot) {
		IAbility props = this;
		boolean doesPlayer = false;
		boolean hasStone = false;
		boolean hasGems = false;
		
		doesPlayer = props.getAbility(slot);
		
		//TODO search through in priority order
		List<ItemStack> playerInv = player.inventory.mainInventory;
		ItemStack backupStone = null;
		for (ItemStack itemStack : playerInv) {
			if (itemStack != null && itemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
				backupStone = backupStone == null ? itemStack : backupStone;
				StoneInventory sInv = new StoneInventory(itemStack);
				if (sInv.getUniqueId().equals(props.getBondedStoneId())) {
					hasStone = true;
					hasGems = sInv.hasGemInSlot(slot);
					break;
				}
			}
		}
		
		// Make this the new stone if the old one couldn't be found.
		if (hasStone == false && backupStone != null) {
			StoneInventory stoneInv = new StoneInventory(backupStone);
			props.setBondedStoneId(stoneInv.getUniqueId());
			hasStone = true;
			hasGems = stoneInv.hasGemInSlot(slot);
		}
		
		if (!doesPlayer) Log.info(slot.name() + " is turned off by the player", player);
		if (!hasStone) Log.info("Attuned sleepstone was not found in inventory", player);
		if (hasStone && !hasGems) Log.info("The sleepstone lacks the neccessary gem(s): " + slot.name(), player);
    	
		return doesPlayer && hasStone && hasGems;
	}

}
