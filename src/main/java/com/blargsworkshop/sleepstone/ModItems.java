package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.items.gems.ItemDensityGem;
import com.blargsworkshop.sleepstone.items.gems.support.density.DensityCraftable;
import com.blargsworkshop.sleepstone.items.stone.ItemSleepstone;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems {
	public static CreativeTabs tabSleepstone;
	
	public static Item itemSleepstone;
	public static Item itemDensityGem;
	public static Item itemDensityCraftable;
	
	public static void init() {
		GameRegistry.registerItem(itemSleepstone = new ItemSleepstone(), "modItemSleepstone");
		GameRegistry.registerItem(itemDensityGem = new ItemDensityGem(), "modItemDensityGem");
		GameRegistry.registerItem(itemDensityCraftable = new DensityCraftable(), "modItemDensityCraftable");
	}
	
	public static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs", 's',
				new ItemStack(Blocks.stone), 'r', new ItemStack(Blocks.redstone_block), 'g',
				new ItemStack(Items.gold_ingot), 'b', new ItemStack(Items.bed));
	}
	
	public static void initCreativeTabs() {
		tabSleepstone = new CreativeTabs(ModInfo.CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public Item getTabIconItem() {
				return ModItems.itemSleepstone;
			}
		};
	}
}
