package com.blargsworkshop.sleepstone.network.packets.toserver;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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
			yo((EntityPlayerMP) player);
			break;
		default:
			break;
		}
	}
	
	private static void yo(EntityPlayerMP player) {
		if (player.getEntityWorld().getBlockState(player.getPosition().north(2)).getMaterial().equals(Material.AIR)) {
			player.getEntityWorld().setBlockState(player.getPosition().north(2), Blocks.STONE.getDefaultState());
		}
	}
	
	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}
}
