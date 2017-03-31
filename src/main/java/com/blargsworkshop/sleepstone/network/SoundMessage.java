package com.blargsworkshop.sleepstone.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;


public class SoundMessage implements IMessage {
	private String filename = null;
	private float volume = 1f;
	private float pitch = 1f;
	
	public SoundMessage(String filename) {
		init(filename, 1f, 1f);
	}
	
	public SoundMessage(String filename, float volume, float pitch) {
		init(filename, volume, pitch);
	}
	
	private void init(String filename, float volume, float pitch) {
		if (filename == null || filename.trim().isEmpty()) {
			//TODO Throw error or something
		}
		
		this.filename = filename;
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
//		this.filename = ByteBufUtils.readUTF8String(buf);		
	}

	@Override
	public void toBytes(ByteBuf buf) {
//		ByteBufUtils.writeUTF8String(buf, filename);		
	}

	public String getFilename() {
		return filename;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}
}
