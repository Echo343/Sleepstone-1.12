package com.blargsworkshop.sleepstone.network.server;

import java.io.IOException;

import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.Utils;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;
import com.blargsworkshop.sleepstone.network.AbstractMessage.AbstractServerMessage;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

public class CommandMessage extends AbstractServerMessage<CommandMessage> {

	public static enum Commands {
		Warp
	}
	
	private Commands command;
	
	public CommandMessage() {}
	
	public CommandMessage(Commands commandEnum) {
		this.command = commandEnum;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		command = Commands.values()[buffer.readInt()];
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(command.ordinal());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		switch (command) {
		case Warp:
			warp((EntityPlayerMP) player);
			break;
		default:
			break;
		}
	}
	
	private void warp(EntityPlayerMP player) {
		if (player.isPotionActive(NovelPotion.warpSickness.id)) {
			Utils.addChatMessage(player, "text.sleepstone.suffering_effects_of_warping");
		}
		else {
			Sleepstone.warpPlayerToBed(player, player.getEntityWorld());
		}
	}
}
