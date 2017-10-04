package com.blargsworkshop.sleepstone.extended_properties;

import com.blargsworkshop.sleepstone.network.PacketDispatcher;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncBoolPlayerPropMessage;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncBoolPlayerPropMessage.ExtendedPlayerFields;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
	private static final String NO_FALL_DAMAGE = "NoFallDamage";

	public final static String EXT_PROP_NAME = "ExtendedPlayer";
	
	private final EntityPlayer player;
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
		properties.setBoolean(NO_FALL_DAMAGE, this.hasNoFallDamage);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.hasNoFallDamage = properties.getBoolean(NO_FALL_DAMAGE);
	}

	@Override
	public void init(Entity entity, World world) {
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
		this.hasNoFallDamage = noFallDamage;
		if (sync) {
			if (isClient()){
				PacketDispatcher.sendToServer(new SyncBoolPlayerPropMessage(ExtendedPlayerFields.NoFallDmg, noFallDamage));
			}
			else {
				PacketDispatcher.sendToPlayer((EntityPlayerMP) player, new SyncBoolPlayerPropMessage(ExtendedPlayerFields.NoFallDmg, noFallDamage));
			}
		}
	}
	
	private boolean isServer() {
		return !player.worldObj.isRemote;
	}
	
	private boolean isClient() {
		return !isServer();
	}

}