package com.blargsworkshop.sleepstone.items.airmattress;

import com.blargsworkshop.engine.tile.IModTileEntity;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBed;

public class AirMattressTileEntity extends TileEntityBed implements IModTileEntity {
	private static final String RESOURCE_NAME = "airmattresste";
	
	@Override
	public String getResourceLocation() {
		return ModInfo.ID + ":" + RESOURCE_NAME;
	}
	
    @Override
	public ItemStack getItemStack()
    {
        return new ItemStack(ModItems.Items.airMattress, 1, this.getColor().getMetadata());
    }

}
