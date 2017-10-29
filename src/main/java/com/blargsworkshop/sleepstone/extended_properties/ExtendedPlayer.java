package com.blargsworkshop.sleepstone.extended_properties;

import java.util.EnumMap;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncPlayerBondedIdMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncPlayerPropMessage;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
	public final static String EXT_PROP_NAME = "ExtendedPlayer";

	private String bondedStoneId = "";
	private EnumMap<Slots, Boolean> abilities = new EnumMap<Slots, Boolean>(Slots.class);
	
	private final EntityPlayer player;
	private static final String BONDED_ID = "BondedStoneId";

	public ExtendedPlayer(EntityPlayer player) {
		this.player = player;
		for (Slots slot : Slots.values()) {
			abilities.put(slot, false);
		}
	}
	
	/**
	 * Used to register these extended properties for the player during EntityConstructing event.
	 * @param player Player to register.
	 */
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
	}

	/**
	 * Returns ExtendedPlayer properties for a player.
	 * @param player Player to return properties from.
	 * @return Returns the extended properties.
	 */
	public static final ExtendedPlayer get(EntityPlayer player) {
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setString(BONDED_ID, this.bondedStoneId);
		// This loop should keep the order every time.
		abilities.forEach((Slots ability, Boolean value) -> {
			properties.setBoolean(ability.name(), value);
		});
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.bondedStoneId = properties.getString(BONDED_ID);
		abilities.forEach((Slots ability, Boolean value) -> {
			abilities.put(ability, properties.getBoolean(ability.name()));
		});
	}
	
	public void syncAll() {
		if (Utils.isServer(player.worldObj)) {
			PacketDispatcher.sendToPlayer(player, new SyncAllPlayerPropsMessage(player));
		}
		else {
			PacketDispatcher.sendToServer(new SyncAllPlayerPropsMessage(player));
		}
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public boolean getAbility(Slots gem) {
		Boolean bool = abilities.get(gem);
		return bool != null ? bool : false;
	}
	
	public void setAbility(Slots gem, boolean flag) {
		setAbility(gem, flag, true);
	}
	
	public void setAbilityWithoutSync(Slots gem, boolean flag) {
		setAbility(gem, flag, false);
	}
	
	protected void setAbility(Slots gem, boolean flag, boolean sync) {
		Boolean abilityValue = abilities.get(gem);
		if (Boolean.valueOf(flag).equals(abilityValue)) {
			return;
		}
		abilityValue = flag;
		abilities.put(gem, abilityValue);
		if (sync) {
			if (Utils.isClient(player.worldObj)) {
				PacketDispatcher.sendToServer(new SyncPlayerPropMessage(gem, abilityValue));
				Log.debug("Setting " + gem + " to " + abilityValue + " on client and syncing to server", this.player);
			}
			else {
				PacketDispatcher.sendToPlayer((EntityPlayerMP) player, new SyncPlayerPropMessage(gem, abilityValue));
				Log.debug("Setting " + gem + " to " + abilityValue + " on server and syncing to client", this.player);
			}
		}
	}
	
	public String getBondedStoneId() {
		return bondedStoneId;
	}

	public void setBondedStoneId(String bondedStoneId) {
		setBondedStoneId(bondedStoneId, true);
	}
	
	public void setBondedStoneId(String bondId, boolean sync) {
		bondId = bondId == null ? "" : bondId.trim();
		if (this.bondedStoneId == bondId) {
			return;
		}
		this.bondedStoneId = bondId;
		if (sync) {
			if (Utils.isClient(player.worldObj)) {
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
