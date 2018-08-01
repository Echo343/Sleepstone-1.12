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

	public AbilityStatus() { }
	
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
	public boolean getAbilityState(Ability ability) {
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
	
	private Integer getCachedStoneIndex() {
		return 1;
	}
	
	private void setCachedStoneIndex(Integer index) {
		//TODO cache the slot index of the bonded stone
		
	}
	
	private ItemStack findBondedStone() {
		//TODO optimize
		ItemStack bondedStone = null;
		IStoneProperties stoneProps;
		
		// Check the cached position first
		ItemStack cache = player.inventory.getStackInSlot(getCachedStoneIndex());
		if (cache.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
			stoneProps = StonePropertiesProvider.getProperties(cache);
			if (stoneProps.getUniqueId().equals(getBondedStoneId())) {
				bondedStone = cache;
			}
		}
		
		if (bondedStone == null) {
			List<ItemStack> playerInv = player.inventory.mainInventory;
			ItemStack backupStone = null;
			Integer backupStoneIndex = null;
			
			for (int i = 0; i < playerInv.size(); i++) {
				ItemStack itemStack = playerInv.get(i);
				if (itemStack.isItemEqual(new ItemStack(ModItems.itemSleepstone))) {
					if (backupStone == null) {
						backupStone = itemStack;
						backupStoneIndex = i;
					}
					stoneProps = StonePropertiesProvider.getProperties(itemStack);
					if (stoneProps.getUniqueId().equals(getBondedStoneId())) {
						bondedStone = itemStack;
						setCachedStoneIndex(i);
						break;
					}
				}
			}
			
			// Make this the new stone if the old one couldn't be found.
			if (bondedStone == null && backupStone != null) {
				stoneProps = StonePropertiesProvider.getProperties(backupStone);
				setBondedStoneId(stoneProps.getUniqueId());
				bondedStone = backupStone;
				setCachedStoneIndex(backupStoneIndex);
			}
		}
		return bondedStone;
	}
	
	@Override
	public boolean isAbilityAvailable(Ability ability) {
		boolean isAvailable = false;
		
		if (getAbilityState(ability)) {
			ItemStack bondedStone = findBondedStone();
			if (bondedStone != null) {
				StoneInventory inventory = StoneInventoryProvider.getStoneInventory(bondedStone);
				isAvailable = inventory.hasGemInSlot(ability);
//				if (!isAvailable) Log.advDebug("The sleepstone lacks the neccessary gem(s): " + ability.name(), player);
			}
			else {
//				Log.advDebug("Attuned sleepstone was not found in inventory", player);
			}
		}
		else {
//			Log.advDebug(ability.name() + " is turned off by the player", player);
		}
		
		return isAvailable;
	}

}
