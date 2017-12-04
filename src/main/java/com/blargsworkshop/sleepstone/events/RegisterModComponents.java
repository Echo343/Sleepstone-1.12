package com.blargsworkshop.sleepstone.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.blargsworkshop.sleepstone.IModItems;
import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.annotations.ModItem;
import com.blargsworkshop.sleepstone.annotations.ModPotion;
import com.blargsworkshop.sleepstone.annotations.ModSound;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegisterModComponents {
	
	protected List<Item> modItems = new ArrayList<>();
	protected List<Potion> modPotions = new ArrayList<>();
	protected List<SoundEvent> modSounds = new ArrayList<>();
	
	public RegisterModComponents(Class<? extends IModItems> class1) {
		try {
			sortAndFilter(class1.getDeclaredFields());
			sortAndFilter(class1.getDeclaredClasses());			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected void sortAndFilter(Class<?>[] innerClasses) throws IllegalArgumentException, IllegalAccessException {
		if (innerClasses == null) { return; }
		
		for (Class<?> innerClass : innerClasses) {
			if (innerClass.isAnnotationPresent(ModItem.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modItems.add((Item) f.get(null));
				}
			}
			else if (innerClass.isAnnotationPresent(ModPotion.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modPotions.add((Potion) f.get(null));
				}
			}
			else if (innerClass.isAnnotationPresent(ModSound.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modSounds.add((SoundEvent) f.get(null));
				}
			}
		}
	}
	
	protected void sortAndFilter(Field[] fields) throws IllegalArgumentException, IllegalAccessException {
		if (fields == null) { return; }
		
		for (Field f : fields) {
			if (f.isAnnotationPresent(ModItem.class)) {
				modItems.add((Item) f.get(null));
			}
			else if (f.isAnnotationPresent(ModPotion.class)) {
				modPotions.add((Potion) f.get(null));
			}
			else if (f.isAnnotationPresent(ModSound.class)) {
				modSounds.add((SoundEvent) f.get(null));
			}
		}
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		Log.detail("RegisterModComponents - Items");
		for (Item i : modItems) {
			event.getRegistry().register(i);
			Log.detail("Registering Item - " + i.getRegistryName());
		}
	}
	
	@SubscribeEvent
	public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		Log.detail("RegisterModComponents - SoundEvents");
		for (SoundEvent s : modSounds) {
			event.getRegistry().register(s);
			Log.detail("Registering Sound - " + s.getRegistryName());
		}
	}
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event) {
		Log.detail("RegisterModComponents - Potions");
		for (Potion p : modPotions) {
			event.getRegistry().register(p);
			Log.detail("Registering Potion - " + p.getRegistryName());
		}
	}
	
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		Log.detail("RegisterModComponents - Recipes");
		// TODO move smelting recipes into ModItems.
		initSmeltingRecipes();
	}
	
	public void initSmeltingRecipes() {
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

	public static CreativeTabs getCreativeTab(String creativeTabName, Supplier<Item> delegate) {
		return new CreativeTabs(creativeTabName) {
			@Override
			public ItemStack getTabIconItem() {
				return new ItemStack(delegate.get());
			}
		};
	}
}
