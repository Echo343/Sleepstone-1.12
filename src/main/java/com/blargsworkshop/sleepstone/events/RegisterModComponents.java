package com.blargsworkshop.sleepstone.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.blargsworkshop.sleepstone.IBlargRecipe;
import com.blargsworkshop.sleepstone.IModItems;
import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.annotations.ModItem;
import com.blargsworkshop.sleepstone.annotations.ModPotion;
import com.blargsworkshop.sleepstone.annotations.ModRecipe;
import com.blargsworkshop.sleepstone.annotations.ModSound;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterModComponents {
	
	protected List<Item> modItems = new ArrayList<>();
	protected List<Potion> modPotions = new ArrayList<>();
	protected List<SoundEvent> modSounds = new ArrayList<>();
	protected List<IBlargRecipe> modRecipes = new ArrayList<>();
	
	public RegisterModComponents(Class<? extends IModItems> class1) {
		try {
			sortAndFilter(class1.getDeclaredFields());
			sortAndFilter(class1.getDeclaredClasses());			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected void sortAndFilter(Field[] fields) throws IllegalArgumentException, IllegalAccessException {
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
			else if (f.isAnnotationPresent(ModRecipe.class)) {
				modRecipes.add((IBlargRecipe) f.get(null));
			}
		}
	}
	
	protected void sortAndFilter(Class<?>[] innerClasses) throws IllegalArgumentException, IllegalAccessException {
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
			else if (innerClass.isAnnotationPresent(ModRecipe.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modRecipes.add((IBlargRecipe) f.get(null));
				}
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
		for (IBlargRecipe p : modRecipes) {
			p.register();
			Log.detail("Registering recipe - " + p.getOutputName());
		}
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
