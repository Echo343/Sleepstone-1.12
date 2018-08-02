package com.blargsworkshop.sleepstone.items.stone.inventory;

import javax.annotation.Nonnull;

import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.items.stone.inventory.gui.StoneSlotType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class StoneInventory extends ItemStackHandler {
    
	private static final String INVENTORY_TAG = "gem_inventory";
	private ItemStack stone = null;

	/** Inventory's size must be the same as number of slots you add to the Container class */
    /** Defining your inventory size this way is handy */
    public static final int INV_SIZE = 27;
    
    public StoneInventory(ItemStack stack) {
    	super(INV_SIZE);
    	stone = stack;
    	onContentsChanged(0);    	
    }
    
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
    
    public NBTTagCompound getTagCompound() {
    	return stone.getTagCompound().getCompoundTag(INVENTORY_TAG);
    }
    
    public void setTagCompound(NBTTagCompound nbt) {
    	stone.getTagCompound().setTag(INVENTORY_TAG, nbt);
    }

    private boolean checkGems(StoneSlotType mainSlot, StoneSlotType augmentSlot) {
    	boolean hasGems = false;
    	ItemStack mainStack = getStackInSlot(mainSlot.ordinal());
    	if (mainStack != null && mainStack.getItem() == mainSlot.getGemItem()) {
			if (augmentSlot != null) {
				ItemStack augmentStack = getStackInSlot(augmentSlot.ordinal());
				if (augmentStack != null && augmentStack.getItem() == augmentSlot.getGemItem()) {
					hasGems = true;
				}
			}
			else {
				hasGems = true;
			}
		}
    	return hasGems;
    }
    
    public boolean hasGemsInSlot(Ability ability) {
    	return checkGems(ability.getFirstGemSlot(), ability.getSecondGemSlot());
    }
}
