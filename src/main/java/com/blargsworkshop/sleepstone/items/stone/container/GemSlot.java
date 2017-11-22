package com.blargsworkshop.sleepstone.items.stone.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.gems.EtherealGem;
import com.blargsworkshop.sleepstone.items.gems.FireGem;
import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.gems.GuardianGem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.StoneGem;
import com.blargsworkshop.sleepstone.items.gems.TimeSpaceGem;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GemSlot extends Slot {
 
    public static List<ResourceLocation> getSpritesToRegister() {
    	List<ResourceLocation> sprites = new ArrayList<>();
    	Set<Class<? extends Gem>> uniqueGemTypes = Utils.getUniqueGemTypes();
    	uniqueGemTypes.forEach(gemType -> sprites.add(getResourceLocationFromGemType(gemType)));
    	return sprites;
    }
	
	private Class<? extends Gem> gemType;
	private final int stackLimit;
	
    public GemSlot(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
        this.gemType = Gem.class;
        this.stackLimit = super.getSlotStackLimit();
    }
    
    public GemSlot(Slots gemSlot, IInventory inv, int xPos, int yPos) {
    	super(inv, gemSlot.ordinal(), xPos, yPos);
    	this.gemType = gemSlot.getGemType();
    	this.stackLimit = 1;
    	this.setBackgroundName(getResourceLocationFromGemType(gemSlot.getGemType()).toString());
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
    
    private static ResourceLocation getResourceLocationFromGemType(Class<? extends Gem> gemType) {
    	ResourceLocation slotTexture = null;
    	if (gemType.equals(StoneGem.class)) {
    		slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-stone");
    	}
    	else if (gemType.equals(PathfinderGem.class)) {
			slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-pathfinder");
    	}
    	else if (gemType.equals(TimeSpaceGem.class)) {
			slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-time-and-space");
    	}
    	else if (gemType.equals(FireGem.class)) {
			slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-fire");
    	}
    	else if (gemType.equals(GuardianGem.class)) {
			slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-guardian");
    	}
    	else if (gemType.equals(EtherealGem.class)) {
			slotTexture = new ResourceLocation(ModInfo.ID, "items/slot-gem-ethereal");
    	}
    	return slotTexture;
    }    
}
