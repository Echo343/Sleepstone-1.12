package com.blargsworkshop.sleepstone.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SoundManager implements IMessageHandler<SoundMessage, IMessage> {
	private static SoundManager instance = null;
	
	public static SoundManager getSoundManager() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	private SoundManager() {	
	
	}
		
	@Override
	public IMessage onMessage(SoundMessage message, MessageContext ctx) {
		String filename = message.getFilename();
		if (filename != null && !filename.trim().isEmpty()) {

		}
		return null;
	}

}
