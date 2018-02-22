package com.blargsworkshop.sleepstone.abilities.temporal_aid.gui;

import com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities.IAidInventory;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.capabilities.TemporalAidProvider;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

public class AidInventoryContainer extends Container {
    private IAidInventory inventory;

    /** Using these will make transferStackInSlot easier to understand and implement
	 * INV_START is the index of the first slot in the Player's Inventory, so our
	 * InventoryItem's number of slots (e.g. 5 slots is array indices 0-4, so start at 5)
	 * Notice how we don't have to remember how many slots we made? We can just use
	 * InventoryItem.INV_SIZE and if we ever change it, the Container updates automatically. */
    private static final int SLOT_START = 0;
    private static final int SLOT_END = StoneInventory.INV_SIZE - 1;
    private static final int INV_START = SLOT_END + 1;
    private static final int INV_END = INV_START + 26;
    private static final int HOTBAR_START = INV_END + 1;
    private static final int HOTBAR_END = HOTBAR_START + 8;
    
    // If you're planning to add armor slots, put those first like this:
	// ARMOR_START = InventoryItem.INV_SIZE, ARMOR_END = ARMOR_START+3,
	// INV_START = ARMOR_END+1, and then carry on like above.
		
    public AidInventoryContainer(World world, EntityPlayer player, InventoryPlayer inventoryPlayer, ItemStack stone) {
        inventory = TemporalAidProvider.getInventory(stone);
        
        /** Gem Slots */
        //Group one
        this.addSlotToContainer(new SlotItemHandler(inventory, 0, 22, 28));
                
        /** Player Inventory - Uses default locations for standard inventory texture file */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }

        /** Player Action Bar - uses default locations for standard action bar texture file */
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 198));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
    	return !player.isDead;
    }

    /**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
    @Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
	{
		ItemStack itemStackCopy = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())	{
			ItemStack itemStack = slot.getStack();
			itemStackCopy = itemStack.copy();

			if (index >= INV_START && index <= HOTBAR_END) {
				if (!this.mergeItemStack(itemStack, SLOT_START, SLOT_END + 1, false)) {
					return ItemStack.EMPTY;
				}
			}
			
			if (itemStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}

			if (itemStack.getCount() == itemStackCopy.getCount()) {
				return ItemStack.EMPTY;
			}
		}
		return itemStackCopy;
    }
    
    /**
	 * You should override this method to prevent the player from moving the stack that
	 * opened the inventory, otherwise if the player moves it, the inventory will not
	 * be able to save properly
	 */
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		// this will prevent the player from interacting with the item that opened the inventory:
		if (slotId >= 0 && getSlot(slotId) != null && getSlot(slotId).getStack() == player.getHeldItemMainhand()) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
    }
}
