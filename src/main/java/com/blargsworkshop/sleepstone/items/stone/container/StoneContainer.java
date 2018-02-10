package com.blargsworkshop.sleepstone.items.stone.container;

import com.blargsworkshop.sleepstone.capabilites.itemstack.StoneInventoryProvider;
import com.blargsworkshop.sleepstone.items.stone.GemSlot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StoneContainer extends Container {
    private StoneInventory inventory;

    /** Using these will make transferStackInSlot easier to understand and implement
	 * INV_START is the index of the first slot in the Player's Inventory, so our
	 * InventoryItem's number of slots (e.g. 5 slots is array indices 0-4, so start at 5)
	 * Notice how we don't have to remember how many slots we made? We can just use
	 * InventoryItem.INV_SIZE and if we ever change it, the Container updates automatically. */
    private static final int GEM_SLOTS_START = 0;
    private static final int GEM_SLOTS_END = GemSlot.values().length - 1;
    private static final int GEM_STORAGE_START = GEM_SLOTS_END + 1;
    private static final int GEM_STORAGE_END = StoneInventory.INV_SIZE - 1;
    private static final int INV_START = GEM_STORAGE_END + 1;
    private static final int INV_END = INV_START + 26;
    private static final int HOTBAR_START = INV_END + 1;
    private static final int HOTBAR_END = HOTBAR_START + 8;
    
    // If you're planning to add armor slots, put those first like this:
	// ARMOR_START = InventoryItem.INV_SIZE, ARMOR_END = ARMOR_START+3,
	// INV_START = ARMOR_END+1, and then carry on like above.
		
    public StoneContainer(World world, EntityPlayer player, InventoryPlayer inventoryPlayer, ItemStack stone) {
        inventory = StoneInventoryProvider.getStoneInventory(stone);
        
        /** Gem Slots */
        //Group one
        this.addSlotToContainer(new GemUISlot(GemSlot.GUARDIAN_1, inventory, 22, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.GUARDIAN_2, inventory, 22, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.GUARDIAN_3, inventory, 22, 68));
        
        this.addSlotToContainer(new GemUISlot(GemSlot.MONK_1, inventory, 40, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.ETHEREAL_1, inventory, 40, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.ELEMENTAL_1, inventory, 40, 68));
        
        //Group two
        this.addSlotToContainer(new GemUISlot(GemSlot.TIMESPACE_1, inventory, 71, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.TIMESPACE_2, inventory, 71, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.TIMESPACE_3, inventory, 71, 68));
                
        this.addSlotToContainer(new GemUISlot(GemSlot.MONK_2, inventory, 89, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.ETHEREAL_2, inventory, 89, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.ELEMENTAL_2, inventory, 89, 68));
        
        //Group three
        this.addSlotToContainer(new GemUISlot(GemSlot.PATHFINDER_1, inventory, 120, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.PATHFINDER_2, inventory, 120, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.PATHFINDER_3, inventory, 120, 68));
        
        this.addSlotToContainer(new GemUISlot(GemSlot.MONK_3, inventory, 138, 28));
        this.addSlotToContainer(new GemUISlot(GemSlot.ETHEREAL_3, inventory, 138, 48));
        this.addSlotToContainer(new GemUISlot(GemSlot.ELEMENTAL_3, inventory, 138, 68));
        
        /** Spare Gem Inventory */
        for (int i = 0; i < 9; i++) {
        	this.addSlotToContainer(new GemUISlot(inventory, i + GemSlot.values().length, 8 + i * 18, 108));
        }

        // If you want, you can add ARMOR SLOTS here as well, but you need to
		// make a public version of SlotArmor. I won't be doing that in this tutorial.
        /** Player Armor */
        /*
		for (i = 0; i < 4; ++i)
		{
			// These are the standard positions for survival inventory layout
			this.addSlotToContainer(new SlotArmor(this.player, inventoryPlayer, inventoryPlayer.getSizeInventory() - 1 - i, 8, 8 + i * 18, i));
		}
        */
        
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

			if (index >= GEM_SLOTS_START && index <= GEM_SLOTS_END) {
				if (!this.mergeItemStack(itemStack, GEM_STORAGE_START, HOTBAR_END + 1, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (index >= GEM_STORAGE_START && index <= GEM_STORAGE_END) {
				if (!this.mergeItemStack(itemStack, GEM_SLOTS_START, GEM_SLOTS_END + 1, false)) {
					if (!this.mergeItemStack(itemStack, INV_START, HOTBAR_END + 1, false)) {					
						return ItemStack.EMPTY;
					}
				}
			}
			else {
				if (!this.mergeItemStack(itemStack, GEM_SLOTS_START, GEM_SLOTS_END + 1, false)) {
					if (!this.mergeItemStack(itemStack, GEM_STORAGE_START, GEM_STORAGE_END + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			
			if (itemStack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemStack.getCount() == itemStackCopy.getCount())
			{
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
