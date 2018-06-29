package com.blargsworkshop.sleepstone.network.packets.toserver;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.abilities.barrier.RockWall;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class CommandMessage extends AbstractServerMessage<CommandMessage> {

	public static enum Command {
		ROCKWALL,
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
		switch (command) {
			case ROCKWALL:
				RockWall rockwall = new RockWall();
				rockwall.generate((EntityPlayerMP) player);
				break;
			case WARP:
				if (player.isPotionActive(Potions.warpSickness)) {
					Utils.addChatMessage(player, "text.sleepstone.suffering_effects_of_warping");
				}
				else {
					Sleepstone.warpPlayerToBed(player);
				}
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
