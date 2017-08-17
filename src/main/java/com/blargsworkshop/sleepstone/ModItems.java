package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.items.gems.DensityGem;
import com.blargsworkshop.sleepstone.items.gems.support.density.DensityCraftable;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;

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
		GameRegistry.registerItem(itemSleepstone = new Sleepstone(), "modItemSleepstone");
		GameRegistry.registerItem(itemDensityGem = new DensityGem(), "modItemDensityGem");
		GameRegistry.registerItem(itemDensityCraftable = new DensityCraftable(), "modItemDensityCraftable");
	}
	
	public static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs",
				's', new ItemStack(Blocks.stone),
				'r', new ItemStack(Blocks.redstone_block),
				'g', new ItemStack(Items.gold_ingot),
				'b', new ItemStack(Items.bed));
		
		/**
		 * item.densitycraftable_0.name=Obsidian Clump
		 * item.densitycraftable_1.name=Hardened Obsidian Clump
		 * item.densitycraftable_2.name=Lattice Diamond Encased Clump
		 * item.densitycraftable_3.name=Hardened Crystallized Encased Structure
		 * item.densitycraftable_4.name=Molded Ceramic Shield Ball
		 * item.densitycraftable_5.name=Blasted Ceramic Encased Orb
		 * item.densitycraftable_6.name=Laced Redstone Encased Orb
		 * item.densitycraftable_7.name=Infused Redstone Orb
		 * item.densitycraftable_8.name=Densely Packed Orb
		 * 
		 * Craftable from Obsidian, diamond blocks, sand, clay, redstone blocks & gold blocks.
		 * */
		GameRegistry.addShapedRecipe(new ItemStack(itemDensityCraftable, 1, 0), "xxx", "xxx", "xxx",
				'x', new ItemStack(Blocks.obsidian));
		GameRegistry.addSmelting(new ItemStack(itemDensityCraftable, 1, 0), new ItemStack(itemDensityCraftable, 1, 1), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemDensityCraftable, 1, 2), "xxx", "xox", "xxx",
				'x', Blocks.diamond_block,
				'o', new ItemStack(itemDensityCraftable, 1, 1));
		GameRegistry.addSmelting(new ItemStack(itemDensityCraftable, 1, 2), new ItemStack(itemDensityCraftable, 1, 3), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemDensityCraftable, 1, 4), "csc", "sos", "csc",
				'c', Blocks.clay,
				's', Blocks.sand,
				'o', new ItemStack(itemDensityCraftable, 1, 3));
		GameRegistry.addSmelting(new ItemStack(itemDensityCraftable, 1, 4), new ItemStack(itemDensityCraftable, 1, 5), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemDensityCraftable, 1, 6), "xxx", "xox", "xxx",
				'x', Blocks.redstone_block,
				'o', new ItemStack(itemDensityCraftable, 1, 5));
		GameRegistry.addSmelting(new ItemStack(itemDensityCraftable, 1, 6), new ItemStack(itemDensityCraftable, 1, 7), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemDensityCraftable, 1, 8), "xxx", "xox", "xxx",
				'x', Blocks.gold_block,
				'o', new ItemStack(itemDensityCraftable, 1, 7));
		GameRegistry.addSmelting(new ItemStack(itemDensityCraftable, 1, 8), new ItemStack(itemDensityGem), 1f);
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
