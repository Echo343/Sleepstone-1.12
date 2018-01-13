package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.relauncher.Side;

public class SyncAllPlayerPropsMessage extends AbstractMessage<SyncAllPlayerPropsMessage> {

	private NBTTagCompound data;
	
	public SyncAllPlayerPropsMessage() {}
	
	public SyncAllPlayerPropsMessage(EntityPlayer player) {
		IStorage<IAbilityStatus> storage = AbilityStatusProvider.ABILITY_STATUS_CAPABILITY.getStorage();
		IAbilityStatus abilityStatus = player.getCapability(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, null);
		data = (NBTTagCompound) storage.writeNBT(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, abilityStatus, null);
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
		IStorage<IAbilityStatus> storage = AbilityStatusProvider.ABILITY_STATUS_CAPABILITY.getStorage();
		IAbilityStatus abilityStatus = player.getCapability(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, null);
		storage.readNBT(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, abilityStatus, null, data);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
