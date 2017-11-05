package com.blargsworkshop.sleepstone;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import com.blargsworkshop.sleepstone.gui.TextureItem;
import com.blargsworkshop.sleepstone.items.EnderShard;
import com.blargsworkshop.sleepstone.items.gems.EtherealGem;
import com.blargsworkshop.sleepstone.items.gems.FireGem;
import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.gems.GuardianGem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.StoneGem;
import com.blargsworkshop.sleepstone.items.gems.TimeSpaceGem;
import com.blargsworkshop.sleepstone.items.gems.mats.PathfinderCraftable;
import com.blargsworkshop.sleepstone.items.gems.mats.StoneCraftable;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;
import com.blargsworkshop.sleepstone.potions.BlargsPotion;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static CreativeTabs tabSleepstone;
	
	public static Item itemSleepstone = new Sleepstone();
	
	public static Gem itemStoneGem = new StoneGem();
	public static Gem itemTimeSpaceGem = new TimeSpaceGem();
	public static Gem itemPathfinderGem = new PathfinderGem();
	public static Gem itemEtherealGem = new EtherealGem();
	public static Gem itemGuardianGem = new GuardianGem();
	public static Gem itemFireGem = new FireGem();
	
	public static Item itemStoneCraftable = new StoneCraftable();
	public static Item itemPathfinderCraftable = new PathfinderCraftable();
	public static Item itemEnderShard = new EnderShard();
	
	public static Item textureGemSlotStone = new TextureItem("textureGemSlotStone", ModInfo.ID + ":slot-gem-stone");
	public static Item textureGemSlotPathfinder = new TextureItem("textureGemSlotPathfinder", ModInfo.ID + ":slot-gem-pathfinder");
	public static Item textureGemSlotTimeAndSpace = new TextureItem("textureGemSlotTimeAndSpace", ModInfo.ID + ":slot-gem-time-and-space");
	public static Item textureGemSlotFire = new TextureItem("textureGemSlotFire", ModInfo.ID + ":slot-gem-fire");
	public static Item textureGemSlotGuardian = new TextureItem("textureGemSlotGuardian", ModInfo.ID + ":slot-gem-guardian");
	public static Item textureGemSlotEthereal = new TextureItem("textureGemSlotEthereal", ModInfo.ID + ":slot-gem-ethereal");
	
	public static class Potions {
		public static BlargsPotion warpSickness;
		public static BlargsPotion enderShardWarp;
	}
	
	
	
	public static void initCreativeTabs() {
		tabSleepstone = new CreativeTabs(ModInfo.CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public Item getTabIconItem() {
				return ModItems.itemSleepstone;
			}
		};
	}
	
	/**
	 * Recreates the potion array to make it longer.
	 * Also init's our custom potions.
	 * Note: I think a later version of Forge handles this for us.
	 */
	public static void preInitPotions() {
		Log.detail("Potions Start - length: " + Potion.potionTypes.length);
		Potion[] potionTypes = null;
		for (Field field : Potion.class.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if (field.getName().equalsIgnoreCase("potionTypes")
						|| field.getName().equalsIgnoreCase("field_76425_a")) {
					Field modField = Field.class.getDeclaredField("modifiers");
					modField.setAccessible(true);
					modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[]) field.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					field.set(null, newPotionTypes);
					break;
				}
			} catch (Exception exc) {
				System.err.println("Error (reflection) - " + exc);
				System.out.println("Error with PotionTypes - " + exc);
			}
		}
		Log.detail("Potions End - length: " + Potion.potionTypes.length);

		int warpSicknessId = -1;
		int enderShardPotionId = -1;
		for (int i = 0; i < Potion.potionTypes.length; i++) {
			if (Potion.potionTypes[i] == null) {
				if (warpSicknessId != -1) {
					enderShardPotionId = i;
					break;
				}
				else {
					warpSicknessId = i;
				}
			}
		}
		if (warpSicknessId == -1 || enderShardPotionId == -1) {
			throw new IndexOutOfBoundsException();
		}
		Potions.warpSickness = new BlargsPotion(warpSicknessId, "potion.warpingsickness");
		Potions.enderShardWarp = new BlargsPotion(enderShardPotionId, "potion.endershard");
	}

	public static void initChestLoot() {
		// This causes an NPE.  Not worth fixing at the moment.
		//Adjust some of the chests to have higher weights
//		adjustChestWeights(ChestGenHooks.DUNGEON_CHEST, 200);
//		adjustChestWeights(ChestGenHooks.MINESHAFT_CORRIDOR, 300);
//		adjustChestWeights(ChestGenHooks.VILLAGE_BLACKSMITH, 100);
		
		Set<Item> gems = Utils.getUniqueGems();
		
		// Add gem chance to each type of loot chest.  (Item, meta, min, max, weight)
		for (Item gem : gems) {
			ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(gem, 0, 0, 1, 1));
			ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(gem, 0, 0, 1, 1));
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(gem, 0, 0, 1, 1));
			ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(gem, 0, 0, 1, 1));
			ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, new WeightedRandomChestContent(gem, 0, 0, 1, 1));
		}
		
		//Add stonecraftables & Sleepstone to Village_Blacksmith chest
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 1, 3, 8, 15));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 3, 1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 4, 1, 4, 9));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 5, 1, 1, 7));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 7, 0, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 10, 1, 64, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 12, 1, 3, 2));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStoneCraftable, 14, 0, 3, 1));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemSleepstone, 0, 1, 1, 5));
	}
	
//	private static void adjustChestWeights(String chestType, int factor) {
//		WeightedRandomChestContent[] chestItems = ChestGenHooks.getItems(chestType, null);
//		int totalChestWeight = 0 ;
//		for (WeightedRandomChestContent item : chestItems) {
//			totalChestWeight += item.itemWeight;
//		}
//		if (totalChestWeight < factor) {
//			int weightFactor = (factor / totalChestWeight) + 1;
//			for (WeightedRandomChestContent item : chestItems) {
//				item.itemWeight *= weightFactor;
//			}
//		}
//	}
}
