package com.blargsworkshop.sleepstone.items.stone.container;

import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.items.stone.GemSlot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class StoneInventory extends ItemStackHandler {

    /** Provides NBT Tag Compound to reference */
//    private final ItemStack invItem;
    
//    private String uniqueId = "";

	/** Inventory's size must be the same as number of slots you add to the Container class */
    /** Defining your inventory size this way is handy */
    public static final int INV_SIZE = 27;
    
    public StoneInventory() {
    	super(INV_SIZE);
    }

    /**
     * Creates a new StoneInventory
     * @param stack - the ItemStack to which this inventory belongs
     */
//	public StoneInventory(ItemStack stack) {
//        invItem = stack;
//
//        // Create a new NBT Tag Compound if one doesn't already exist, or you will crash
//		if (!stack.hasTagCompound()) {
//			stack.setTagCompound(new NBTTagCompound());
//			uniqueId = UUID.randomUUID().toString();
//			markDirty();
//		}
//
//		// Read the inventory contents from NBT
//		readFromNBT(stack.getTagCompound());
//    }

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
//
//    /**
//	 * This is the method that will handle saving the inventory contents, as it is called (or should be called!)
//	 * anytime the inventory changes. Perfect. Much better than using onUpdate in an Item, as this will also
//	 * let you change things in your inventory without ever opening a Gui, if you want.
//	 */
//	@Override
//	public void markDirty()
//	{
//		// TODO might need this to set to ItemStack.EMPTY
////		for (int i = 0; i < getSizeInventory(); ++i)
////		{
////			if (getStackInSlot(i) != null && getStackInSlot(i).getCount() == 0) {
////				inventory.set(i, null);
////			}
////		}
//		
//		// This line here does the work:		
//		writeToNBT(invItem.getTagCompound());
//    }
//    
//    /**
//	 * A custom method to read our inventory from an ItemStack's NBT compound
//     * @param compound 
//	 */
//	public void readFromNBT(NBTTagCompound compound)
//	{
//		uniqueId = compound.getString("uniqueId");
//		if ("".equals(uniqueId)) {
//			uniqueId = UUID.randomUUID().toString();
//			
//		}
//		// Gets the custom taglist we wrote to this compound, if any
//		NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);
//
//		for (int i = 0; i < items.tagCount(); ++i)
//		{
//			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
//			int slot = item.getInteger("Slot");
//
//			// Just double-checking that the saved slot index is within our inventory array bounds
//			if (slot >= 0 && slot < getSizeInventory()) {
//				inventory.set(slot, new ItemStack(item));
//			}
//		}
//	}
//
//	/**
//	 * A custom method to write our inventory to an ItemStack's NBT compound
//	 * @param tagcompound 
//	 */
//	public void writeToNBT(NBTTagCompound tagcompound)
//	{
//		// Create a new NBT Tag List to store itemstacks as NBT Tags
//		NBTTagList items = new NBTTagList();
//
//		for (int i = 0; i < getSizeInventory(); ++i)
//		{
//			// Only write stacks that contain items
//			if (getStackInSlot(i) != null)
//			{
//				// Make a new NBT Tag Compound to write the itemstack and slot index to
//				NBTTagCompound item = new NBTTagCompound();
//				item.setInteger("Slot", i);
//				// Writes the itemstack in slot(i) to the Tag Compound we just made
//				item = getStackInSlot(i).writeToNBT(item);
//
//				// add the tag compound to our tag list
//				items.appendTag(item);
//			}
//		}
//		// Add the UID to the Tag Compound
//		tagcompound.setString("uniqueId", this.uniqueId);
//		// Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
//		tagcompound.setTag("ItemInventory", items);
//	}
//
//	public String getUniqueId() {
//		return uniqueId;
//	}
}
