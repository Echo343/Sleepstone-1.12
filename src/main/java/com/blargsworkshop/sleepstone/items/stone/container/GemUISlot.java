package com.blargsworkshop.sleepstone.items.stone.container;

import com.blargsworkshop.sleepstone.ModItems.Sprites;
import com.blargsworkshop.sleepstone.items.gems.ElementalGem;
import com.blargsworkshop.sleepstone.items.gems.EtherealGem;
import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.gems.GuardianGem;
import com.blargsworkshop.sleepstone.items.gems.MonkGem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.TimeSpaceGem;
import com.blargsworkshop.sleepstone.items.stone.GemSlot;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GemUISlot extends SlotItemHandler {
	
	private Class<? extends Gem> gemType;
	private final int stackLimit;
	
    public GemUISlot(IItemHandler inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
        this.gemType = Gem.class;
        this.stackLimit = super.getSlotStackLimit();
    }
    
    public GemUISlot(GemSlot gemSlot, IItemHandler inv, int xPos, int yPos) {
    	super(inv, gemSlot.ordinal(), xPos, yPos);
    	this.gemType = gemSlot.getGemItem().getClass();
    	this.stackLimit = 1;
    	this.setBackgroundName(getResourceLocationFromGemType(gemSlot.getGemItem().getClass()).toString());
    }
    
//    @Override
//    public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_)
//    {
//    	this.getItemHandler().
//    }

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
    
    private ResourceLocation getResourceLocationFromGemType(Class<? extends Gem> gemType) {
    	ResourceLocation slotTexture = null;
    	if (gemType.equals(MonkGem.class)) {
    		slotTexture = Sprites.monkSlotBackground;
    	}
    	else if (gemType.equals(PathfinderGem.class)) {
			slotTexture = Sprites.pathfinderSlotBackground;
    	}
    	else if (gemType.equals(TimeSpaceGem.class)) {
			slotTexture = Sprites.timeSpaceSlotBackground;
    	}
    	else if (gemType.equals(ElementalGem.class)) {
			slotTexture = Sprites.elementalSlotBackground;
    	}
    	else if (gemType.equals(GuardianGem.class)) {
			slotTexture = Sprites.guardianSlotBackground;
    	}
    	else if (gemType.equals(EtherealGem.class)) {
			slotTexture = Sprites.etherealSlotBackground;
    	}
    	return slotTexture;
    }
}
