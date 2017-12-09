package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.relauncher.Side;

public class SyncAllPlayerPropsMessage extends AbstractMessage<SyncAllPlayerPropsMessage> {

	private NBTTagCompound data;
	
	public SyncAllPlayerPropsMessage() {}
	
	public SyncAllPlayerPropsMessage(EntityPlayer player) {
		IStorage<IAbility> storage = AbilityProvider.ABILITY_CAPABILITY.getStorage();
		IAbility abilities = player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null);
		data = (NBTTagCompound) storage.writeNBT(AbilityProvider.ABILITY_CAPABILITY, abilities, null);
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		data = buffer.readCompoundTag();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeCompoundTag(data);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		IStorage<IAbility> storage = AbilityProvider.ABILITY_CAPABILITY.getStorage();
		IAbility abilities = player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null);
		storage.readNBT(AbilityProvider.ABILITY_CAPABILITY, abilities, null, data);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
