package com.blargsworkshop.sleepstone.network.server;

import java.io.IOException;

import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.network.AbstractMessage.AbstractServerMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

/**
 * A simple message telling the server that the client wants to open a GUI.
 */
public class OpenGuiMessage extends AbstractServerMessage<OpenGuiMessage> {

	private int guiId;
	
	public OpenGuiMessage() {}
	
	public OpenGuiMessage(GuiEnum gui) {
		this.guiId = gui.ordinal();
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		guiId = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(guiId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		player.openGui(SleepstoneMod.instance, this.guiId, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	}

}