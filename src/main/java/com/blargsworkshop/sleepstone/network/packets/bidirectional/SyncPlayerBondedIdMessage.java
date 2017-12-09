package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SyncPlayerBondedIdMessage extends AbstractMessage<SyncPlayerBondedIdMessage> {
	
	private String bondedId = "";
	
	public SyncPlayerBondedIdMessage() {}
	
	public SyncPlayerBondedIdMessage(String bondedId) {
		this.bondedId = bondedId == null ? "" : bondedId.trim();
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		int len = buffer.readInt();
		bondedId = buffer.readString(len);
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(bondedId.length());
		buffer.writeString(bondedId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IAbility props = player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null);
		props.setBondedStoneIdWithoutSync(bondedId);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
