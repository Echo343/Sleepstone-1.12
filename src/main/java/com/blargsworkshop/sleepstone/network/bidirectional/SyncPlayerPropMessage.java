package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class SyncPlayerPropMessage extends AbstractMessage<SyncPlayerPropMessage> {
	
	private ExtendedPlayer.PlayerFields field;
	private boolean boolValue;
	private String stringValue = "";
	
	public SyncPlayerPropMessage() {}
	
	public SyncPlayerPropMessage(ExtendedPlayer.PlayerFields field, boolean value) {
		this.field = field;
		this.boolValue = value;
	}
	
	public SyncPlayerPropMessage(ExtendedPlayer.PlayerFields field, String value) {
		this.field = field;
		this.stringValue = value == null ? "" : value.trim();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		field = ExtendedPlayer.PlayerFields.values()[buffer.readInt()];
		switch (field) {
		case BondedStoneId:
			int len = buffer.readInt();
			stringValue = buffer.readStringFromBuffer(len);
			break;
		case NoFallDmg:
			boolValue = buffer.readBoolean();
			break;
		}
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(field.ordinal());
		switch (field) {
		case BondedStoneId:
			buffer.writeInt(stringValue.length());
			buffer.writeStringToBuffer(stringValue);
			break;
		case NoFallDmg:
			buffer.writeBoolean(boolValue);
			break;
		}
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ExtendedPlayer extPlayer = ExtendedPlayer.get(player);
		switch (field) {
		case BondedStoneId:
			extPlayer.setBondedStoneId(stringValue, false);
			break;
		case NoFallDmg:
			extPlayer.setNoFallDamage(boolValue, false);
			break;
		}
	}

}
