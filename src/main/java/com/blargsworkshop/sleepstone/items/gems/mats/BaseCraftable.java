package com.blargsworkshop.sleepstone.items.gems.mats;

import java.util.HashMap;
import java.util.Map;

import com.blargsworkshop.engine.item.ISubtypable;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.BaseModItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public abstract class BaseCraftable extends BaseModItem implements ISubtypable {
	private final int NUMBER_OF_CRAFTABLES;
	private final String REGISTRY_NAME;
	
	public BaseCraftable(String unlocalizedName, String registryName, int numberOfCraftables) {
		super(unlocalizedName, registryName);
		this.REGISTRY_NAME = registryName;
		this.NUMBER_OF_CRAFTABLES = numberOfCraftables;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	public int getNumberOfCraftables() {
		return NUMBER_OF_CRAFTABLES;
	}
	
	public Map<Integer, ResourceLocation> getResourceLocationMap() {
		Map<Integer, ResourceLocation> resourceMap = new HashMap<>(getNumberOfCraftables(), 1f);
		for (int i = 0; i < getNumberOfCraftables(); i++) {
			resourceMap.put(i, new ResourceLocation(ModInfo.ID + ":" + REGISTRY_NAME + "_" + i));
		}
		return resourceMap;
	}
	
	@Override
	public void getSubItems( CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab))
        {
			for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
				items.add(new ItemStack(this, 1, i));
			}
        }
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getMetadata();
	}
}
