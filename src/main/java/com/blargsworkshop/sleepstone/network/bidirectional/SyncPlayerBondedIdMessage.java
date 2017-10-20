package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class SyncPlayerBondedIdMessage extends AbstractMessage<SyncPlayerBondedIdMessage> {
	
	private String bondedId = "";
	
	public SyncPlayerBondedIdMessage() {}
	
	public SyncPlayerBondedIdMessage(String bondedId) {
		this.bondedId = bondedId == null ? "" : bondedId.trim();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		int len = buffer.readInt();
		bondedId = buffer.readStringFromBuffer(len);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(bondedId.length());
		buffer.writeStringToBuffer(bondedId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ExtendedPlayer extPlayer = ExtendedPlayer.get(player);
		extPlayer.setBondedStoneId(bondedId, false);
	}

}
