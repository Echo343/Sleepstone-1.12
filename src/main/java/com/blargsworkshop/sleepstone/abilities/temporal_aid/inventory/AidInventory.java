package com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class AidInventory extends ItemStackHandler implements IAidInventory {
    
	private static final String INVENTORY_TAG = "temporalaid_inventory";
	private ItemStack stone = null;

	public AidInventory(ItemStack stack) {
    	super(INV_SIZE);
    	stone = stack;
    	onContentsChanged(0);    	
    }
    
    @Override
	public ItemStack getStone() {
    	return stone;
    }
    
    @Override
    protected void onContentsChanged(int slot) {
    	if (!stone.hasTagCompound()) {
    		NBTTagCompound tagComp = new NBTTagCompound();
			tagComp.setTag(INVENTORY_TAG, this.serializeNBT());
    		stone.setTagCompound(tagComp);			
		}
    	else {
    		setTagCompound(this.serializeNBT());
    	}
    }
    
    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        onContentsChanged(slot);
    }
    
    @Override
	public NBTTagCompound getTagCompound() {
    	return stone.getTagCompound().getCompoundTag(INVENTORY_TAG);
    }
    
    @Override
	public void setTagCompound(NBTTagCompound nbt) {
    	stone.getTagCompound().setTag(INVENTORY_TAG, nbt);
    }
}
