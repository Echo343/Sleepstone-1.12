package com.blargsworkshop.sleepstone.items.stone.container;

import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GemSlot extends Slot {
	private Class<? extends Gem> gemType;
	private final int stackLimit;
	
    public GemSlot(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
        this.gemType = Gem.class;
        this.stackLimit = super.getSlotStackLimit();
    }
    
    public GemSlot(Slots gemSlot, IInventory inv, int xPos, int yPos, IIcon ico) {
    	super(inv, gemSlot.ordinal(), xPos, yPos);
    	this.gemType = gemSlot.getGemType();
    	this.stackLimit = 1;
    	if (ico != null) {
    		this.setBackgroundIcon(ico);
    	}
    }

    // This is the only method we need to override so that
    // we can't place our inventory-storing Item within
    // its own inventory (thus making it permanently inaccessible)
    // as well as preventing abuse of storing backpacks within backpacks
    /**
     * Check if the stack is a valid item for this slot
     */
    @Override
    public boolean isItemValid(ItemStack itemStack) {
//        return !(itemStack.getItem() instanceof Sleepstone); // Does not allow Sleepstone to store itself.
        return gemType.isInstance(itemStack.getItem()); //Only allows the initialized Gem.
    }
    
    @Override
    public int getSlotStackLimit() {
    	return stackLimit;
    }
    
    public Class<? extends Gem> getGemType() {
    	return gemType;
    }
    
}
