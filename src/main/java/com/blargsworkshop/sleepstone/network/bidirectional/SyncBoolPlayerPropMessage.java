package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class SyncBoolPlayerPropMessage extends AbstractMessage<SyncBoolPlayerPropMessage> {
	
	public static enum ExtendedPlayerFields {
		NoFallDmg
	}

	private ExtendedPlayerFields field;
	private boolean value;
	
	public SyncBoolPlayerPropMessage() {}
	
	public SyncBoolPlayerPropMessage(ExtendedPlayerFields field, boolean value) {
		this.field = field;
		this.value = value;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		field = ExtendedPlayerFields.values()[buffer.readInt()];
		value = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(field.ordinal());
		buffer.writeBoolean(value);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ExtendedPlayer extPlayer = ExtendedPlayer.get(player);
		switch (field) {
		case NoFallDmg:
			extPlayer.setNoFallDamage(value, false);
			break;
		}
	}

}
