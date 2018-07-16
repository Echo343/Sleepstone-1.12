package com.blargsworkshop.sleepstone.network.packets.toserver;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.abilities.barrier.NatureWall;
import com.blargsworkshop.sleepstone.abilities.helljumper.Helljump;
import com.blargsworkshop.sleepstone.abilities.warp.Warp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class CommandMessage extends AbstractServerMessage<CommandMessage> {

	public static enum Command {
		HELLJUMP,
		LEAFWALL,
		WARP
	}
	
	private Command command;
	
	public CommandMessage() {}
	
	public CommandMessage(Command commandEnum) {
		this.command = commandEnum;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		command = Command.values()[buffer.readInt()];
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(command.ordinal());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			switch (command) {
				case HELLJUMP:
					new Helljump(playerMP).startJump();
					break;
				case LEAFWALL:
					NatureWall.INSTANCE.generate(playerMP);
					break;
				case WARP:
					Warp.INSTANCE.startWarp(playerMP);
					break;
				default:
					break;
			}
		}
	}
		
	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}
}
