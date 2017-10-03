package com.blargsworkshop.sleepstone.network.server;

import java.io.IOException;

import com.blargsworkshop.sleepstone.NovelPotion;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;
import com.blargsworkshop.sleepstone.network.AbstractMessage.AbstractServerMessage;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentText;

public class CommandMessage extends AbstractServerMessage<CommandMessage> {

	public static enum Commands {
		Warp
	}
	
	private int commandId;
	
	public CommandMessage() {}
	
	public CommandMessage(Commands commandEnum) {
		this.commandId = commandEnum.ordinal();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		commandId = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(commandId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if (playerMP.isPotionActive(NovelPotion.warpSickness.id)) {
			playerMP.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("text.sleepstone.suffering_effects_of_warping")));
		}
		else {
			Sleepstone.warpPlayerToBed(playerMP, playerMP.getEntityWorld());
		}
	}
}
