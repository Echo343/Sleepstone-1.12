package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
			ModItems.itemEnderShard
		);
	}
	
	public static void initSmeltingRecipes() {
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
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 0), new ItemStack(ModItems.itemStoneCraftable, 1, 1), 0.1f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 2), new ItemStack(ModItems.itemStoneCraftable, 1, 3), 1f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 6), new ItemStack(ModItems.itemStoneCraftable, 1, 7), 1f);
		GameRegistry.addSmelting(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModItems.itemStoneCraftable, 1, 8), 0.4f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 9), new ItemStack(ModItems.itemStoneCraftable, 1, 10), 0.7f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 11), new ItemStack(ModItems.itemStoneCraftable, 1, 12), 1f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 13), new ItemStack(ModItems.itemStoneCraftable, 1, 14), 1f);
		GameRegistry.addSmelting(new ItemStack(ModItems.itemStoneCraftable, 1, 15), new ItemStack(ModItems.itemStoneGem), 1f);
	}

	public static CreativeTabs getCreativeTab() {
		return new CreativeTabs(ModInfo.CREATIVE_TAB_SLEEPSTONE) {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(ModItems.itemSleepstone);
			}
		};
	}
}
