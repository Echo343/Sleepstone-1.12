package com.blargsworkshop.sleepstone.capabilites.player;

import java.util.EnumMap;
import java.util.Map;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncPlayerBondedIdMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncPlayerPropMessage;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Ability implements IAbility {
	
	private String bondedStoneId = "";
	private EntityPlayer player;
	private EnumMap<Slots, Boolean> abilities = new EnumMap<Slots, Boolean>(Slots.class);

	public void init(EntityPlayer player) {
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
			PacketDispatcher.sendToPlayer(player, new SyncAllPlayerPropsMessage(player));
		}
		else {
			PacketDispatcher.sendToServer(new SyncAllPlayerPropsMessage(player));
		}
	}
	
	protected void setAbility(Slots gem, boolean value, boolean doSync) {
		if (Boolean.valueOf(value).equals(abilities.get(gem))) {
			return;
		}
		abilities.put(gem, value);
		if (doSync) {
			if (Utils.isClient(player.getEntityWorld())) {
				PacketDispatcher.sendToServer(new SyncPlayerPropMessage(gem, value));
				Log.debug("Setting " + gem + " to " + value + " on client and syncing to server", this.player);
			}
			else {
				PacketDispatcher.sendToPlayer((EntityPlayerMP) player, new SyncPlayerPropMessage(gem, value));
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
				PacketDispatcher.sendToServer(new SyncPlayerBondedIdMessage(bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on client", this.player);
			}
			else {
				PacketDispatcher.sendToPlayer(player, new SyncPlayerBondedIdMessage(bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on server", this.player);
			}
		}
	}

}
