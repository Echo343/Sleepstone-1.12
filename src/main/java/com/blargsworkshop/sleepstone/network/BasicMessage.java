package com.blargsworkshop.sleepstone.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BasicMessage implements IMessage {
	
	public static enum Commands_old {
		WARP,
		OpenInvGui
	}
	
	protected Commands_old command;
	
	public BasicMessage(Commands_old command) {
		this.command = command;
	}
	
	public BasicMessage() {
		
	}
	
	public void setCommand(Commands_old command) {
		this.command = command;
	}
	
	public Commands_old getCommand() {
		return this.command;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int commandInt = ByteBufUtils.readVarInt(buf, 4);
		this.command = Commands_old.values()[commandInt];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.command.ordinal(), 4);
	}

}
