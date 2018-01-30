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
    private static final int INV_START = StoneInventory.INV_SIZE;
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
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If item is in our custom Inventory or armor slot
			if (index < INV_START)
			{
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, false))
				{
					return ItemStack.EMPTY;
				}

//				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place in custom inventory or armor slots
			else
			{
				/*
				If your inventory only stores certain instances of Items,
				you can implement shift-clicking to your inventory like this:
				
				// Check that the item is the right type
				if (itemstack1.getItem() instanceof ItemCustom)
				{
					// Try to merge into your custom inventory slots
					// We use 'InventoryItem.INV_SIZE' instead of INV_START just in case
					// you also add armor or other custom slots
					if (!this.mergeItemStack(itemstack1, 0, InventoryItem.INV_SIZE, false))
					{
						return null;
					}
				}
				// If you added armor slots, check them here as well:
				// Item being shift-clicked is armor - try to put in armor slot
				if (itemstack1.getItem() instanceof ItemArmor)
				{
					int type = ((ItemArmor) itemstack1.getItem()).armorType;
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false))
					{
						return null;
					}
				}
				Otherwise, you have basically 2 choices:
				1. shift-clicking between player inventory and custom inventory
				2. shift-clicking between action bar and inventory
				 
				Be sure to choose only ONE of the following implementations!!!
				*/
				/**
				 * Implementation number 1: Shift-click into your custom inventory
				 */
				if (index >= INV_START)
				{
					// place in custom inventory
					if (!this.mergeItemStack(itemstack1, 0, INV_START, false))
					{
						return ItemStack.EMPTY;
					}
				}
				
				/**
				 * Implementation number 2: Shift-click items between action bar and inventory
				 */
				// item is in player's inventory, but not in action bar
				// if (index >= INV_START && index < HOTBAR_START)
				// {
				// 	// place in action bar
				// 	if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END+1, false))
				// 	{
				// 		return null;
				// 	}
				// }
				// // item in action bar - place in player inventory
				// else if (index >= HOTBAR_START && index < HOTBAR_END+1)
				// {
				// 	if (!this.mergeItemStack(itemstack1, INV_START, INV_END+1, false))
				// 	{
				// 		return null;
				// 	}
				// }
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

//			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
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
