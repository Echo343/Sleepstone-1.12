package com.blargsworkshop.sleepstone.network.packets.toserver;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

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
		player.openGui(SleepstoneMod.getInstance(), this.guiId, player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
