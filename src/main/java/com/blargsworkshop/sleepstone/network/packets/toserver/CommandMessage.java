package com.blargsworkshop.sleepstone.network.packets.toserver;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.powers.RockWall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class CommandMessage extends AbstractServerMessage<CommandMessage> {

	public static enum Command {
		ROCKWALL
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
		switch (command) {
		case ROCKWALL:
			RockWall rockwall = new RockWall();
			rockwall.generate((EntityPlayerMP) player);
			break;
		default:
			break;
		}
	}
		
	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}
}
