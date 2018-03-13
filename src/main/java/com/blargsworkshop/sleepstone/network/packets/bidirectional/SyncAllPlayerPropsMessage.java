package com.blargsworkshop.sleepstone.network.packets.bidirectional;

import java.io.IOException;

import com.blargsworkshop.engine.network.AbstractMessage;
import com.blargsworkshop.engine.proxy.IProxy;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.relauncher.Side;

public class SyncAllPlayerPropsMessage extends AbstractMessage<SyncAllPlayerPropsMessage> {

	private NBTTagCompound data;
	
	public SyncAllPlayerPropsMessage() {}
	
	public SyncAllPlayerPropsMessage(EntityPlayer player) {
		IStorage<IAbilityStatus> storage = AbilityStatusProvider.getCapability().getStorage();
		IAbilityStatus abilityStatus = AbilityStatusProvider.getAbilityStatus(player);
		data = (NBTTagCompound) storage.writeNBT(AbilityStatusProvider.getCapability(), abilityStatus, null);
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
		IStorage<IAbilityStatus> storage = AbilityStatusProvider.getCapability().getStorage();
		IAbilityStatus abilityStatus = AbilityStatusProvider.getAbilityStatus(player);
		storage.readNBT(AbilityStatusProvider.getCapability(), abilityStatus, null, data);
	}

	@Override
	protected IProxy getProxy() {
		return SleepstoneMod.getProxy();
	}

}
