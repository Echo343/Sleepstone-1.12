package com.blargsworkshop.sleepstone.items.stone.container;

import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.items.stone.GemSlot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class StoneInventory extends ItemStackHandler {
    
	/** Inventory's size must be the same as number of slots you add to the Container class */
    /** Defining your inventory size this way is handy */
    public static final int INV_SIZE = 27;
    private ItemStack stone = null;
    
    public StoneInventory(ItemStack stack) {
    	super(INV_SIZE);
    	stone = stack;
    	if (!stack.hasTagCompound()) {
			stack.setTagCompound(this.serializeNBT());
		}
    }
    
    public ItemStack getStone() {
    	return stone;
    }
    
    @Override
    protected void onContentsChanged(int slot) {
    	stone.setTagCompound(this.serializeNBT());
    }

    private boolean checkGems(GemSlot mainSlot, GemSlot augmentSlot) {
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
    
    public boolean hasGemInSlot(Ability ability) {
    	return checkGems(ability.getFirstGemSlot(), ability.getSecondGemSlot());
    }
}
