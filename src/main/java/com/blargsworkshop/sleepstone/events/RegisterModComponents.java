package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.gems.mats.StoneCraftable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class RegisterModComponents {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		// TODO change this to reflection
		event.getRegistry().registerAll(
				ModItems.itemSleepstone,
				ModItems.itemStoneGem,
				ModItems.itemTimeSpaceGem,
				ModItems.itemPathfinderGem,
				ModItems.itemEtherealGem,
				ModItems.itemGuardianGem,
				ModItems.itemFireGem,

				ModItems.itemStoneCraftable,
				ModItems.itemPathfinderCraftable,
				ModItems.itemEnderShard,
				
				ModItems.textureGemSlotStone,
				ModItems.textureGemSlotPathfinder,
				ModItems.textureGemSlotTimeAndSpace,
				ModItems.textureGemSlotFire,
				ModItems.textureGemSlotGuardian,
				ModItems.textureGemSlotEthereal
			);
		
//		GameRegistry.registerItem(itemSleepstone = new Sleepstone(), "modItemSleepstone");
//		
//		GameRegistry.registerItem(itemStoneGem = new StoneGem(), "modItemStoneGem");
//		GameRegistry.registerItem(itemTimeSpaceGem = new TimeSpaceGem(), "modItemTimeSpaceGem");
//		GameRegistry.registerItem(itemPathfinderGem = new PathfinderGem(), "modItemPathfinderGem");
//		GameRegistry.registerItem(itemEtherealGem = new EtherealGem(), "modItemEtherealGem");
//		GameRegistry.registerItem(itemGuardianGem = new GuardianGem(), "modItemGuardianGem");
//		GameRegistry.registerItem(itemFireGem = new FireGem(), "modItemFireGem");
//		
//		GameRegistry.registerItem(itemStoneCraftable = new StoneCraftable(), "modItemStoneCraftable");
//		GameRegistry.registerItem(itemPathfinderCraftable = new PathfinderCraftable(), "modItemPathfinderCraftable");
//		GameRegistry.registerItem(itemEnderShard = new EnderShard(), "modItemEnderShard");
//		
//		GameRegistry.registerItem(textureGemSlotStone, "modTextureGemSlotStone");
//		GameRegistry.registerItem(textureGemSlotPathfinder, "modTextureGemSlotPathfinder");
//		GameRegistry.registerItem(textureGemSlotTimeAndSpace, "modTextureGemSlotTimeAndSpace");
//		GameRegistry.registerItem(textureGemSlotFire, "modTextureGemSlotFire");
//		GameRegistry.registerItem(textureGemSlotGuardian, "modTextureGemSlotGuardian");
//		GameRegistry.registerItem(textureGemSlotEthereal, "modTextureGemSlotEthereal");
	}
	
	@SubscribeEvent
	public static void registerTextures(ModelRegistryEvent event) {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(ModItems.itemSleepstone, 0, new ModelResourceLocation(ModInfo.ID + ":" + "sleepstone"));
		mesher.register(ModItems.itemStoneGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_stone"));
		mesher.register(ModItems.itemTimeSpaceGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_timeandspace"));
		mesher.register(ModItems.itemPathfinderGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_pathfinder"));
		mesher.register(ModItems.itemEtherealGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_ethereal"));
		mesher.register(ModItems.itemGuardianGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_guardian"));
		mesher.register(ModItems.itemFireGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_fire"));
		
		// TODO do something with this.
		for (int i = 0; i < StoneCraftable.NUMBER_OF_CRAFTABLES; i++) {
			mesher.register(ModItems.itemStoneCraftable, i, new ModelResourceLocation(ModInfo.ID + ":" + "craftablestone_" + i));
		}
		mesher.register(ModItems.itemPathfinderCraftable, 0, new ModelResourceLocation(ModInfo.ID + ":" + "craftablepathfinder"));
		mesher.register(ModItems.itemEnderShard, 0, new ModelResourceLocation(ModInfo.ID + ":" + "endershard"));
		
		mesher.register(ModItems.textureGemSlotStone, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-stone"));
		mesher.register(ModItems.textureGemSlotPathfinder, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-pathfinder"));
		mesher.register(ModItems.textureGemSlotTimeAndSpace, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-time-and-space"));
		mesher.register(ModItems.textureGemSlotFire, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-fire"));
		mesher.register(ModItems.textureGemSlotGuardian, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-guardian"));
		mesher.register(ModItems.textureGemSlotEthereal, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-ethereal"));
	}
	
	public static void initRecipes() {
		/** Sleepstone */
//		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs",
//				's', new ItemStack(Blocks.stone),
//				'r', new ItemStack(Blocks.redstone_block),
//				'g', new ItemStack(Items.gold_ingot),
//				'b', new ItemStack(Items.bed));
		
		/** Pathfinder Gem */
//		for (int i = 0; i < 16; i++) {
//			GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderCraftable),
//					Items.feather,
//					new ItemStack(Blocks.wool, 1, i),
//					Items.milk_bucket,
//					new ItemStack(Items.dye, 1, 0)); // Ink Sac
//		}
//		GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderGem),
//				Items.pumpkin_seeds,
//				new ItemStack(Items.dye, 1, 2), // Cactus Green
//				new ItemStack(Blocks.double_plant, 1, 4), //Rose Bush
//				new ItemStack(Items.dye, 1, 3), // Coco Beans
//				Items.saddle,
//				new ItemStack(itemPathfinderCraftable), // Mob Essence
//				Items.snowball,
//				Items.slime_ball,
//				Items.emerald);
//		// Copies the above recipe, but changes pumpkin and melon seeds
//		GameRegistry.addShapelessRecipe(new ItemStack(itemPathfinderGem),
//				Items.melon_seeds,
//				new ItemStack(Items.dye, 1, 2), // Cactus Green
//				new ItemStack(Blocks.double_plant, 1, 4), //Rose Bush
//				new ItemStack(Items.dye, 1, 3), // Coco Beans
//				Items.saddle,
//				new ItemStack(itemPathfinderCraftable), // Mob Essence
//				Items.snowball,
//				Items.slime_ball,
//				Items.emerald);
		
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
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 0), "ccc", "csc", "ccc",
//				'c', Blocks.hardened_clay,
//				's', Blocks.sandstone);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 0), new ItemStack(ModItems.itemStoneCraftable, 1, 1), 0.1f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 2), "xxx", "xcx", "xxx",
//				'x', new ItemStack(itemStoneCraftable, 1, 1),
//				'c', new ItemStack(Items.coal, 1, 1));
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 2), new ItemStack(ModItems.itemStoneCraftable, 1, 3), 1f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 4), "xxx", "xxx", "xxx",
//				'x', Blocks.redstone_block);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 5), "grg", "rgr", "grg",
//				'r', new ItemStack(itemStoneCraftable, 1, 4),
//				'g', Blocks.gold_block);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 6), " r ", "rcr", " r ",
//				'r', new ItemStack(itemStoneCraftable, 1, 5),
//				'c', new ItemStack(itemStoneCraftable, 1, 3));
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 6), new ItemStack(ModItems.itemStoneCraftable, 1, 7), 1f);
		GameRegistry.addSmelting(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModItems.itemStoneCraftable, 1, 8), 0.4f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 9), "ooo", "odo", "ooo",
//				'o', new ItemStack(itemStoneCraftable, 1, 8),
//				'd', Items.diamond);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 9), new ItemStack(ModItems.itemStoneCraftable, 1, 10), 0.7f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 11), "d d", " d ", "d d",
//				'd', Items.diamond);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 11), new ItemStack(ModItems.itemStoneCraftable, 1, 12), 1f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 13), "ooo", "odo", "ooo",
//				'o', new ItemStack(itemStoneCraftable, 1, 10),
//				'd', new ItemStack(itemStoneCraftable, 1, 12));
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 13), new ItemStack(ModItems.itemStoneCraftable, 1, 14), 1f);
//		GameRegistry.addShapedRecipe(new ItemStack(itemStoneCraftable, 1, 15), "ccc", "cic", "ccc",
//				'c', new ItemStack(itemStoneCraftable, 1, 14),
//				'i', new ItemStack(itemStoneCraftable, 1, 7));
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 15), new ItemStack(ModItems.itemStoneGem), 1f);
		
		// Time and Space Gem
//		GameRegistry.addShapedRecipe(new ItemStack(itemTimeSpaceGem), "xxx", "xex", "xxx",
//				'x', Blocks.end_stone,
//				'e', Items.ender_eye);
	}

	public static void initCreativeTabs() {
		ModItems.tabSleepstone = new CreativeTabs(ModInfo.CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.itemSleepstone);
			}
		};
	}
}
