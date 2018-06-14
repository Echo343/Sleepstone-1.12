package com.blargsworkshop.sleepstone.items.airmattress;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBed;

public class AirMattressTileEntity extends TileEntityBed {

    @Override
	public ItemStack getItemStack()
    {
        return new ItemStack(ModItems.Items.airMattress, 1, this.getColor().getMetadata());
    }

}
