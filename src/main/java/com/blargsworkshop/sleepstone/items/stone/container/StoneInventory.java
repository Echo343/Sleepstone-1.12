package com.blargsworkshop.sleepstone.items.stone.container;

import java.util.List;
import java.util.UUID;

import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;

public class StoneInventory implements IInventory {
    private String name = "text.stoneinventory.sleepstone";

    /** Provides NBT Tag Compound to reference */
    private final ItemStack invItem;
    
    private String uniqueId = "";

    /** Defining your inventory size this way is handy */
    public static final int INV_SIZE = 21;

    /** Inventory's size must be the same as number of slots you add to the Container class */
    private List<ItemStack> inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);

    /**
     * @param itemstack - the ItemStack to which this inventory belongs
     */
	public StoneInventory(ItemStack stack) {
        invItem = stack;

        // Create a new NBT Tag Compound if one doesn't already exist, or you will crash
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			uniqueId = UUID.randomUUID().toString();
			markDirty();
		}

		// Read the inventory contents from NBT
		readFromNBT(stack.getTagCompound());
    }
    
    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.get(slot);
    }
    
    private boolean checkGems(Slots mainSlot, Slots augmentSlot) {
    	boolean hasGems = false;
    	ItemStack mainStack = getStackInSlot(mainSlot.ordinal());
    	if (mainStack != null && mainStack.getItem() == mainSlot.getItem()) {
			if (augmentSlot != null) {
				ItemStack augmentStack = getStackInSlot(augmentSlot.ordinal());
				if (augmentStack != null && augmentStack.getItem() == augmentSlot.getItem()) {
					hasGems = true;
				}
			}
			else {
				hasGems = true;
			}
		}
    	return hasGems;
    }
    
    public boolean hasGemInSlot(Slots slot) {
    	boolean hasGems = false;
    	switch (slot) {
    	case Stone:
    	case TimeSpace:
    	case Pathfinder:
    		hasGems = checkGems(slot, null);
    		break;
    	case StoneEthereal:
    	case StoneGuardian:
    	case StoneFire:
    		hasGems = checkGems(Slots.Stone, slot);
			break;
		case TimeSpaceEthereal:
		case TimeSpaceGuardian:
		case TimeSpaceFire:
			hasGems = checkGems(Slots.TimeSpace, slot);
			break;
		case PathfinderEthereal:
		case PathfinderGuardian:
		case PathfinderFire:
			hasGems = checkGems(Slots.Pathfinder, slot);
			break;
    	}
    	return hasGems;
    }

    @Override
	public ItemStack decrStackSize(int slot, int amount)
	{
    	ItemStack stack = ItemStack.EMPTY;
    	
    	if (!inventory.get(slot).isEmpty()) {
    		stack = ItemStackHelper.getAndSplit(inventory, slot, amount);
    	}
    	
    	return stack;
    	
//		ItemStack stack = getStackInSlot(slot);
//		if(stack != null && !stack.isEmpty())
//		{
//			if(stack.getCount() > amount)
//			{
//				stack = stack.splitStack(amount);
//				// Don't forget this line or your inventory will not be saved!
//				markDirty();
//			}
//			else
//			{
//				// this method also calls markDirty, so we don't need to call it again
//				setInventorySlotContents(slot, null);
//			}
//		}
//		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory.set(slot, stack);

		if (stack != null && stack.getCount() > getInventoryStackLimit())
		{
			stack.setCount(getInventoryStackLimit());
		}

		// Don't forget this line or your inventory will not be saved!
		markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
	 * This is the method that will handle saving the inventory contents, as it is called (or should be called!)
	 * anytime the inventory changes. Perfect. Much better than using onUpdate in an Item, as this will also
	 * let you change things in your inventory without ever opening a Gui, if you want.
	 */
	@Override
	public void markDirty()
	{
		// TODO might need this to set to ItemStack.EMPTY
//		for (int i = 0; i < getSizeInventory(); ++i)
//		{
//			if (getStackInSlot(i) != null && getStackInSlot(i).getCount() == 0) {
//				inventory.set(i, null);
//			}
//		}
		
		// This line here does the work:		
		writeToNBT(invItem.getTagCompound());
    }
    
    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
    	if (entityPlayer.isDead)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
    }
    
    /**
	 * A custom method to read our inventory from an ItemStack's NBT compound
	 */
	public void readFromNBT(NBTTagCompound compound)
	{
		uniqueId = compound.getString("uniqueId");
		if ("".equals(uniqueId)) {
			uniqueId = UUID.randomUUID().toString();
			
		}
		// Gets the custom taglist we wrote to this compound, if any
		NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			// Just double-checking that the saved slot index is within our inventory array bounds
			if (slot >= 0 && slot < getSizeInventory()) {
				inventory.set(slot, new ItemStack(item));
			}
		}
	}

	/**
	 * A custom method to write our inventory to an ItemStack's NBT compound
	 */
	public void writeToNBT(NBTTagCompound tagcompound)
	{
		// Create a new NBT Tag List to store itemstacks as NBT Tags
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			// Only write stacks that contain items
			if (getStackInSlot(i) != null)
			{
				// Make a new NBT Tag Compound to write the itemstack and slot index to
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				// Writes the itemstack in slot(i) to the Tag Compound we just made
				item = getStackInSlot(i).writeToNBT(item);

				// add the tag compound to our tag list
				items.appendTag(item);
			}
		}
		// Add the UID to the Tag Compound
		tagcompound.setString("uniqueId", this.uniqueId);
		// Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
		tagcompound.setTag("ItemInventory", items);
	}

	public String getUniqueId() {
		return uniqueId;
	}

	@Override
	public String getName() {
        return name;
	}

	@Override
	public boolean hasCustomName() {
        return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : inventory) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = inventory.get(index);
		if (!stack.isEmpty())
        {
            inventory.set(index, ItemStack.EMPTY);
            return stack;
        }
        else
        {
            return ItemStack.EMPTY;
        }
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory.clear();
	}
}
