package com.blargsworkshop.sleepstone.items.stone.properties;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StoneProperties implements IStoneProperties {

	private static final String UNIQUE_ID = "uniqueid";
	private ItemStack stone = null;
	
	public StoneProperties() { }
	
	public StoneProperties(ItemStack stone) {
		this.stone = stone;
		String uniqueId = UUID.randomUUID().toString();
		setUniqueId(uniqueId);
	}

	@Override
	public String getUniqueId() {
		return stone.getTagCompound().getString(UNIQUE_ID);
	}
	
	@Override
	public void setUniqueId(String uniqueId) {
		if (!stone.hasTagCompound()) {
    		NBTTagCompound tagComp = new NBTTagCompound();
			tagComp.setString(UNIQUE_ID, uniqueId);
			stone.setTagCompound(tagComp);			
		}
		else {
			stone.getTagCompound().setString(UNIQUE_ID, uniqueId);
		}
	}

}
