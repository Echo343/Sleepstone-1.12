package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.gui.TextureItem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.StoneGem;
import com.blargsworkshop.sleepstone.items.gems.support.PathfinderCraftable;
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
	public static Item itemPathfinderGem;
	public static Item itemPathfinderCraftable;
	public static Item itemStoneGem;
	public static Item itemStoneCraftable;
	
	public static Item textureGemSlotStone = new TextureItem("textureGemSlotStone", ModInfo.ID + ":slot-gem-stone");
	public static Item textureGemSlotPathfinder = new TextureItem("textureGemSlotPathfinder", ModInfo.ID + ":slot-gem-pathfinder");
	public static Item textureGemSlotTimeAndSpace = new TextureItem("textureGemSlotTimeAndSpace", ModInfo.ID + ":slot-gem-time-and-space");
	public static Item textureGemSlotFire = new TextureItem("textureGemSlotFire", ModInfo.ID + ":slot-gem-fire");
	public static Item textureGemSlotGuardian = new TextureItem("textureGemSlotGuardian", ModInfo.ID + ":slot-gem-guardian");
	public static Item textureGemSlotEthereal = new TextureItem("textureGemSlotEthereal", ModInfo.ID + ":slot-gem-ethereal");
	
	public static void init() {
		GameRegistry.registerItem(itemSleepstone = new Sleepstone(), "modItemSleepstone");
		GameRegistry.registerItem(itemPathfinderGem = new PathfinderGem(), "modItemPathfinderGem");
		GameRegistry.registerItem(itemPathfinderCraftable = new PathfinderCraftable(), "modItemPathfinderCraftable");
		GameRegistry.registerItem(itemStoneGem = new StoneGem(), "modItemStoneGem");
		GameRegistry.registerItem(itemStoneCraftable = new StoneCraftable(), "modItemStoneCraftable");
		
		GameRegistry.registerItem(textureGemSlotStone, "modTextureGemSlotStone");
		GameRegistry.registerItem(textureGemSlotPathfinder, "modTextureGemSlotPathfinder");
		GameRegistry.registerItem(textureGemSlotTimeAndSpace, "modTextureGemSlotTimeAndSpace");
		GameRegistry.registerItem(textureGemSlotFire, "modTextureGemSlotFire");
		GameRegistry.registerItem(textureGemSlotGuardian, "modTextureGemSlotGuardian");
		GameRegistry.registerItem(textureGemSlotEthereal, "modTextureGemSlotEthereal");
	}
	
	public static void initRecipes() {
		/** Sleepstone */
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs",
				's', new ItemStack(Blocks.stone),
				'r', new ItemStack(Blocks.redstone_block),
				'g', new ItemStack(Items.gold_ingot),
				'b', new ItemStack(Items.bed));
		
		/** Pathfinder Gem */
		for (int i = 0; i < 16; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderCraftable),
					Items.feather,
					new ItemStack(Blocks.wool, 1, i),
					Items.milk_bucket,
					new ItemStack(Items.dye, 1, 0)); // Ink Sac
		}
		GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderGem),
				Items.pumpkin_seeds,
				new ItemStack(Items.dye, 1, 2), // Cactus Green
				new ItemStack(Blocks.double_plant, 1, 4), //Rose Bush
				new ItemStack(Items.dye, 1, 3), // Coco Beans
				Items.saddle,
				new ItemStack(itemPathfinderCraftable), // Mob Essence
				Items.snowball,
				Items.slime_ball,
				Items.emerald);
		// Copies the above recipe, but changes pumpkin and melon seeds
		GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderGem),
				Items.melon_seeds,
				new ItemStack(Items.dye, 1, 2), // Cactus Green
				new ItemStack(Blocks.double_plant, 1, 4), //Rose Bush
				new ItemStack(Items.dye, 1, 3), // Coco Beans
				Items.saddle,
				new ItemStack(itemPathfinderCraftable), // Mob Essence
				Items.snowball,
				Items.slime_ball,
				Items.emerald);
		
		/** Stone Gem
		 * item.stonecraftable_0.name=Hardened Clay Piece
		 * item.stonecraftable_1.name=Blasted Clay Piece
		 * item.stonecraftable_2.name=Ceramic Foundation
		 * item.stonecraftable_3.name=Blasted Ceramic Foundation
		 * item.stonecraftable_4.name=Condensed Redstone Block
		 * item.stonecraftable_5.name=Hyper Lattice Redstone Block
		 * item.stonecraftable_6.name=Hyper Lattice Redstone Mass
		 * item.stonecraftable_7.name=Hyper Infused Mass
		 * item.stonecraftable_8.name=Refined Obsidian
		 * item.stonecraftable_9.name=Refined Obsidian Clump
		 * item.stonecraftable_10.name=Hardened Obsidian Clump
		 * item.stonecraftable_11.name=Brittle Diamond Lattice
		 * item.stonecraftable_12.name=Strengthened Diamond Lattice
		 * item.stonecraftable_13.name=Crystalline Lattice Structure
		 * item.stonecraftable_14.name=Heated Crystalline Lattice Structure
		 * item.stonecraftable_15.name=Radial Empowered Orb
		 * */
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 0), "ccc", "csc", "ccc",
				'c', Blocks.hardened_clay,
				's', Blocks.sandstone);
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 0), new ItemStack(itemStoneCraftable, 1, 1), 0.1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 2), "xxx", "xcx", "xxx",
				'x', new ItemStack(itemStoneCraftable, 1, 1),
				'c', new ItemStack(Items.coal, 1, 1));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 2), new ItemStack(itemStoneCraftable, 1, 3), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 4), "xxx", "xxx", "xxx",
				'x', Blocks.redstone_block);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 5), "grg", "rgr", "grg",
				'r', new ItemStack(itemStoneCraftable, 1, 4),
				'g', Blocks.gold_block);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 6), " r ", "rcr", " r ",
				'r', new ItemStack(itemStoneCraftable, 1, 5),
				'c', new ItemStack(itemStoneCraftable, 1, 3));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 6), new ItemStack(itemStoneCraftable, 1, 7), 1f);
		GameRegistry.addSmelting(new ItemStack(Blocks.obsidian), new ItemStack(itemStoneCraftable, 1, 8), 0.4f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 9), "ooo", "odo", "ooo",
				'o', new ItemStack(itemStoneCraftable, 1, 8),
				'd', Items.diamond);
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 9), new ItemStack(itemStoneCraftable, 1, 10), 0.7f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 11), "d d", " d ", "d d",
				'd', Items.diamond);
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 11), new ItemStack(itemStoneCraftable, 1, 12), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 13), "ooo", "odo", "ooo",
				'o', new ItemStack(itemStoneCraftable, 1, 10),
				'd', new ItemStack(itemStoneCraftable, 1, 12));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 13), new ItemStack(itemStoneCraftable, 1, 14), 1f);
		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 15), "ccc", "cic", "ccc",
				'c', new ItemStack(itemStoneCraftable, 1, 14),
				'i', new ItemStack(itemStoneCraftable, 1, 7));
		GameRegistry.addSmelting(new ItemStack(itemStoneCraftable, 1, 15), new ItemStack(itemStoneGem), 1f);		
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
