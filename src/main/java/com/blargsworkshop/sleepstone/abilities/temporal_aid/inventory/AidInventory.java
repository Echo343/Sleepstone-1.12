package com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory;

import javax.annotation.Nonnull;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.TemporalAidProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class AidInventory extends ItemStackHandler implements IAidInventory {
    
	private static final String INVENTORY_TAG = "temporalaid_inventory";
	private ItemStack stone = null;
	private EntityPlayer player = null;

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
	public void setTarget(@Nonnull EntityPlayer owner) {
		player = owner;
	}
    
    @Override
    protected void onContentsChanged(int slot) {
    	if (player != null && slot == 0 && !this.getStackInSlot(slot).isEmpty()) {
    		ItemStack teleportItem = this.getStackInSlot(0);
    		this.stacks.set(0, ItemStack.EMPTY);
    		TemporalAidProvider.getTarget(player).getTarget().dropItem(teleportItem, true, false);

    		if (!stone.hasTagCompound()) {
    			NBTTagCompound tagComp = new NBTTagCompound();
    			tagComp.setTag(INVENTORY_TAG, this.serializeNBT());
    			stone.setTagCompound(tagComp);			
    		}
    		else {
    			setTagCompound(this.serializeNBT());
    		}
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
