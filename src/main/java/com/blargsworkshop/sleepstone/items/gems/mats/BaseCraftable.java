package com.blargsworkshop.sleepstone.items.gems.mats;

import java.util.HashMap;
import java.util.Map;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.BaseItem;

import net.minecraft.util.ResourceLocation;

public abstract class BaseCraftable extends BaseItem {
	private final int NUMBER_OF_CRAFTABLES;
	private final String REGISTRY_NAME;
	
	public BaseCraftable(String unlocalizedName, String registryName, int numberOfCraftables) {
		super(unlocalizedName, registryName);
		this.REGISTRY_NAME = registryName;
		this.NUMBER_OF_CRAFTABLES = numberOfCraftables;
		this.setHasSubtypes(true);
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
	
//	private final String UNLOCALIZED_NAME;
	
//	public IIcon[] icons;

//	public BaseCraftable(String unlocalizedName, int numberOfCraftables) {
//		super(unlocalizedName);
//		this.UNLOCALIZED_NAME = unlocalizedName;
//		this.NUMBER_OF_CRAFTABLES = numberOfCraftables;
//		icons = new IIcon[NUMBER_OF_CRAFTABLES];
//		this.setHasSubtypes(true);
//	}
//
//	@Override
//	public void registerIcons(IIconRegister reg) {
//		for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
//			this.icons[i] = reg.registerIcon(ModInfo.ID + ":" + UNLOCALIZED_NAME + "_" + i);
//		}
//	}
//	
//	@Override
//	public IIcon getIconFromDamage(int meta) {
//		meta %= NUMBER_OF_CRAFTABLES;
//		return this.icons[meta];
//	}
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public void getSubItems(Item item, CreativeTabs tab, List list) {
//		for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
//			list.add(new ItemStack(item, 1, i));
//		}
//	}
//	
//	@Override
//	public String getUnlocalizedName(ItemStack stack) {
//		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
//	}
}
