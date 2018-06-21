package com.blargsworkshop.sleepstone.items;

import java.util.HashMap;
import java.util.Map;

import com.blargsworkshop.engine.item.ISubtypable;
import com.blargsworkshop.sleepstone.ModInfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public abstract class BaseSubtype extends BaseModItem implements ISubtypable {
	private final int numberOfSubtypes;
	protected final String registryName;
	
	public BaseSubtype(String unlocalizedName, String registryName, int numberOfSubtypes) {
		super(unlocalizedName, registryName);
		this.registryName = registryName;
		this.numberOfSubtypes = numberOfSubtypes;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	protected int getNumberOfSubtypes() {
		return numberOfSubtypes;
	}
	
	@Override
	public Map<Integer, ResourceLocation> getResourceLocationMap() {
		Map<Integer, ResourceLocation> resourceMap = new HashMap<>(getNumberOfSubtypes(), 1f);
		for (int i = 0; i < getNumberOfSubtypes(); i++) {
			resourceMap.put(i, new ResourceLocation(ModInfo.ID + ":" + registryName + "_" + i));
		}
		return resourceMap;
	}
	
	@Override
	public void getSubItems( CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab))
        {
			for (int i = 0; i < numberOfSubtypes; i++) {
				items.add(new ItemStack(this, 1, i));
			}
        }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getMetadata();
	}
}
