package com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public interface IAidInventory extends IItemHandler {

	/**
	 * Inventory's size must be the same as number of slots you add to the Container class
	 * Defining your inventory size this way is handy
	 */
	static final int INV_SIZE = 1;

	ItemStack getStone();

	NBTTagCompound getTagCompound();

	void setTagCompound(NBTTagCompound nbt);

}