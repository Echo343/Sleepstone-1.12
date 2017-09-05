package com.blargsworkshop.sleepstone.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BasicMessage implements IMessage {
	
	public static enum Commands {
		WARP,
		OpenInvGui
	}
	
	protected Commands command;
	
	public BasicMessage(Commands command) {
		this.command = command;
	}
	
	public BasicMessage() {
		
	}
	
	public void setCommand(Commands command) {
		this.command = command;
	}
	
	public Commands getCommand() {
		return this.command;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int commandInt = ByteBufUtils.readVarInt(buf, 4);
		this.command = Commands.values()[commandInt];
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, this.command.ordinal(), 4);
	}

}
