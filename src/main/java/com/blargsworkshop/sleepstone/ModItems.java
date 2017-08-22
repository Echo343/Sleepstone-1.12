package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.items.gems.StoneGem;
import com.blargsworkshop.sleepstone.items.gems.support.StoneCraftable;
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
	public static Item itemStoneGem;
	public static Item itemStoneCraftable;
	
	public static void init() {
		GameRegistry.registerItem(itemSleepstone = new Sleepstone(), "modItemSleepstone");
		GameRegistry.registerItem(itemStoneGem = new StoneGem(), "modItemStoneGem");
		GameRegistry.registerItem(itemStoneCraftable = new StoneCraftable(), "modItemStoneCraftable");
	}
	
	public static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs",
				's', new ItemStack(Blocks.stone),
				'r', new ItemStack(Blocks.redstone_block),
				'g', new ItemStack(Items.gold_ingot),
				'b', new ItemStack(Items.bed));
		
		/**
		 * item.stonecraftable_0.name=Obsidian Clump
		 * item.stonecraftable_1.name=Hardened Obsidian Clump
		 * item.stonecraftable_2.name=Lattice Diamond Encased Clump
		 * item.stonecraftable_3.name=Hardened Crystallized Encased Structure
		 * item.stonecraftable_4.name=Molded Ceramic Shield Ball
		 * item.stonecraftable_5.name=Blasted Ceramic Encased Orb
		 * item.stonecraftable_6.name=Laced Redstone Encased Orb
		 * item.stonecraftable_7.name=Infused Redstone Orb
		 * item.stonecraftable_8.name=Densely Packed Orb
		 * 
		 * Craftable from Obsidian, diamond blocks, sand, clay, redstone blocks & gold blocks.
		 * */
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 0), "xxx", "xxx", "xxx",
				'x', new ItemStack(Blocks.obsidian));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 0), new ItemStack(itemStoneCraftable, 1, 1), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 2), "xxx", "xox", "xxx",
				'x', Blocks.diamond_block,
				'o', new ItemStack(itemStoneCraftable, 1, 1));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 2), new ItemStack(itemStoneCraftable, 1, 3), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 4), "csc", "sos", "csc",
				'c', Blocks.clay,
				's', Blocks.sand,
				'o', new ItemStack(itemStoneCraftable, 1, 3));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 4), new ItemStack(itemStoneCraftable, 1, 5), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 6), "xxx", "xox", "xxx",
				'x', Blocks.redstone_block,
				'o', new ItemStack(itemStoneCraftable, 1, 5));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 6), new ItemStack(itemStoneCraftable, 1, 7), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 8), "xxx", "xox", "xxx",
				'x', Blocks.gold_block,
				'o', new ItemStack(itemStoneCraftable, 1, 7));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 8), new ItemStack(itemStoneGem), 1f);
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
