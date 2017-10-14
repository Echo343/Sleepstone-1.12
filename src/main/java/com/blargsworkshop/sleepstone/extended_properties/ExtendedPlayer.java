package com.blargsworkshop.sleepstone.extended_properties;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncPlayerPropMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
	public static enum PlayerFields {
		BondedStoneId,
		NoFallDmg
	}

	private static final String BONDED_ID = "BondedStoneId";
	private static final String NO_FALL_DAMAGE = "NoFallDamage";

	public final static String EXT_PROP_NAME = "ExtendedPlayer";
	
	private final EntityPlayer player;
	private String bondedStoneId = "";
	private boolean hasNoFallDamage = false;

	public ExtendedPlayer(EntityPlayer player) {
		this.player = player;
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
		properties.setBoolean(NO_FALL_DAMAGE, this.hasNoFallDamage);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.bondedStoneId = properties.getString(BONDED_ID);
		this.hasNoFallDamage = properties.getBoolean(NO_FALL_DAMAGE);
	}
	
	public void syncAll() {
		if (isServer()) {
			PacketDispatcher.sendToPlayer(player, new SyncAllPlayerPropsMessage(player));
		}
		else {
			PacketDispatcher.sendToServer(new SyncAllPlayerPropsMessage(player));
		}
	}

	@Override
	public void init(Entity entity, World world) {
	}

	private boolean isServer() {
		return !player.worldObj.isRemote;
	}
	
	private boolean isClient() {
		return !isServer();
	}

	public boolean getNoFallDamage() {
		return hasNoFallDamage;
	}

	/**
	 * Sets the noFallDamage flag.
	 * This will auto sync.
	 * To set without syncing, use overloaded method.
	 * @param noFallDamage
	 */
	public void setNoFallDamage(boolean noFallDamage) {
		setNoFallDamage(noFallDamage, true);
	}
	
	public void setNoFallDamage(boolean noFallDamage, boolean sync) {
		if (this.hasNoFallDamage == noFallDamage) {
			return;
		}
		this.hasNoFallDamage = noFallDamage;
		if (sync) {
			if (isClient()) {
				PacketDispatcher.sendToServer(new SyncPlayerPropMessage(ExtendedPlayer.PlayerFields.NoFallDmg, noFallDamage));
				Log.debug("Setting noFall to " + getNoFallDamage() + " on client", this.player);
			}
			else {
				PacketDispatcher.sendToPlayer((EntityPlayerMP) player, new SyncPlayerPropMessage(ExtendedPlayer.PlayerFields.NoFallDmg, noFallDamage));
				Log.debug("Setting noFall to " + getNoFallDamage() + " on server", this.player);
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
			if (isClient()) {
				PacketDispatcher.sendToServer(new SyncPlayerPropMessage(ExtendedPlayer.PlayerFields.BondedStoneId, bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on client", this.player);
			}
			else {
				PacketDispatcher.sendToPlayer(player, new SyncPlayerPropMessage(ExtendedPlayer.PlayerFields.BondedStoneId, bondedStoneId));
				Log.debug("Setting UUID to " + getBondedStoneId() + " on server", this.player);
			}
		}
	}
	
	public boolean isGemTurnedOn(Slots slot) {
		switch (slot) {
		case Stone:
			return getNoFallDamage();
		default:
			return false;
		}
	}


}
