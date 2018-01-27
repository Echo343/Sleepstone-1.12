package com.blargsworkshop.sleepstone.capabilites.itemstack;

import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.IItemHandler;

public class StoneInventoryProvider implements ICapabilitySerializable<NBTBase> {
	
	// TODO make private and test Same with other one?
	@CapabilityInject(IItemHandler.class)
	public static final Capability<IItemHandler> STONE_INVENTORY_CAPABILITY = null;
	
	private IItemHandler inventory;

	public StoneInventoryProvider() {
		inventory = new StoneInventory();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == STONE_INVENTORY_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == STONE_INVENTORY_CAPABILITY ? STONE_INVENTORY_CAPABILITY.<T> cast(inventory) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return STONE_INVENTORY_CAPABILITY.getStorage().writeNBT(STONE_INVENTORY_CAPABILITY, inventory, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		STONE_INVENTORY_CAPABILITY.getStorage().readNBT(STONE_INVENTORY_CAPABILITY, inventory, null, nbt);
	}

	public static StoneInventory getStoneInventory(ItemStack stack) {
		return (StoneInventory) stack.getCapability(STONE_INVENTORY_CAPABILITY, EnumFacing.NORTH);
	}
}
