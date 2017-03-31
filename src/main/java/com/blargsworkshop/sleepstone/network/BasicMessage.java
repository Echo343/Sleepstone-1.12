package com.blargsworkshop.sleepstone.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BasicMessage implements IMessage {
	
	protected String messageText;
	
	public BasicMessage(String message) {
		messageText = message;
	}
	
	public BasicMessage() {
		
	}
	
	public void setMessage(String message) {
		messageText = message;
	}
	
	public String getMessage() {
		return messageText;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		messageText = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, messageText);
	}

}
