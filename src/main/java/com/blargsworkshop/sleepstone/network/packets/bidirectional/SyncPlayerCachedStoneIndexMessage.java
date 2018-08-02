package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SyncPlayerCachedStoneIndexMessage extends AbstractMessage<SyncPlayerCachedStoneIndexMessage> {
	
	private int cachedStoneIndex = -1;
	
	public SyncPlayerCachedStoneIndexMessage() {}
	
	public SyncPlayerCachedStoneIndexMessage(int cachedIndex) {
		this.cachedStoneIndex = cachedIndex < -1 ? -1 : cachedIndex;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		cachedStoneIndex = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(cachedStoneIndex);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IAbilityStatus props = AbilityStatusProvider.getAbilityStatus(player);
		props.setCachedStoneIndexWithoutSync(cachedStoneIndex);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
