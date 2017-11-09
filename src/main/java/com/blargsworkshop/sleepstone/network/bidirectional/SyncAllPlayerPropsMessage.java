package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SyncAllPlayerPropsMessage extends AbstractMessage<SyncAllPlayerPropsMessage> {

	private NBTTagCompound data;
	
	public SyncAllPlayerPropsMessage() {}
	
	public SyncAllPlayerPropsMessage(EntityPlayer player) {
		data = new NBTTagCompound();
//		ExtendedPlayer.get(player).saveNBTData(data);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		data = buffer.readCompoundTag();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeCompoundTag(data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
//		ExtendedPlayer.get(player).loadNBTData(data);
	}

}
