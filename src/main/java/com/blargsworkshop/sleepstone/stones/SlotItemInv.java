package com.blargsworkshop.sleepstone.stones;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;

public class SlotItemInv extends Slot {
    public SlotItemInv(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
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
        return !(itemStack.getItem() instanceof ItemSleepstone);
    }
}
