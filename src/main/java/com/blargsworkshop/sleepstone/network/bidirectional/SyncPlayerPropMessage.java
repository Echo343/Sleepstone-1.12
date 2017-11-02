package com.blargsworkshop.sleepstone.network.bidirectional;

import java.io.IOException;

import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.network.AbstractMessage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class SyncPlayerPropMessage extends AbstractMessage<SyncPlayerPropMessage> {
	
	private Slots ability;
	private boolean bool;
	
	public SyncPlayerPropMessage() {}
	
	public SyncPlayerPropMessage(Slots ability, boolean value) {
		this.ability = ability;
		this.bool = value;
	}
	
	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		ability = Slots.values()[buffer.readInt()];
		bool = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(ability.ordinal());
		buffer.writeBoolean(bool);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		ExtendedPlayer extPlayer = ExtendedPlayer.get(player);
		extPlayer.setAbilityWithoutSync(ability, bool);
	}

}
