package com.blargsworkshop.sleepstone.abilities.temporal_aid;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.network.AbstractMessage.AbstractServerMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.gui.GuiEnum;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class OpenAidGuiMessage extends AbstractServerMessage<OpenAidGuiMessage> {

	private UUID destinationPlayerUUID = null;
	
	public OpenAidGuiMessage() {}
	
	public OpenAidGuiMessage(@Nonnull UUID uuid) {
		destinationPlayerUUID = uuid;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		int strLen = buffer.readInt();
		destinationPlayerUUID = UUID.fromString(buffer.readString(strLen));
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(destinationPlayerUUID.toString().length());
		buffer.writeString(destinationPlayerUUID.toString());
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		if (side == Side.SERVER) {
			if (destinationPlayerUUID == null) {
				Log.error("Selected player's UUID == null");
			}
			PlayerList players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
			EntityPlayer destPlayer = players.getPlayerByUUID(destinationPlayerUUID);
			player.openGui(SleepstoneMod.getInstance(), GuiEnum.TEMPORAL_AID_INVENTORY.ordinal(), player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
		
	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}
}
