package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SyncPlayerPropMessage extends AbstractMessage<SyncPlayerPropMessage> {
	
	private Ability ability;
	private boolean bool;
	
	public SyncPlayerPropMessage() {}
	
	public SyncPlayerPropMessage(Ability ability, boolean value) {
		this.ability = ability;
		this.bool = value;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		ability = Ability.values()[buffer.readInt()];
		bool = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(ability.ordinal());
		buffer.writeBoolean(bool);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IAbilityStatus props = player.getCapability(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, null);
		props.setAbilityWithoutSync(ability, bool);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
