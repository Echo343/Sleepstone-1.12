package com.blargsworkshop.sleepstone;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.annotations.ModItem;
import com.blargsworkshop.engine.annotations.ModPotion;
import com.blargsworkshop.engine.annotations.ModRecipe;
import com.blargsworkshop.engine.annotations.ModSound;
import com.blargsworkshop.engine.annotations.ModSprite;
import com.blargsworkshop.engine.event.RegisterModComponents;
import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.recipe.IBlargRecipe;
import com.blargsworkshop.engine.recipe.SmeltingRecipe;
import com.blargsworkshop.engine.sound.BlargsSoundEvent;
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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * All the items in the mod.
 */
public class ModItems implements IModItems{
	public static CreativeTabs tabSleepstone = RegisterModComponents.getCreativeTab(ModInfo.CREATIVE_TAB_SLEEPSTONE, () -> ModItems.itemSleepstone);
	
	@ModItem
	public static Item itemSleepstone = new Sleepstone();
	
	@ModItem
	public static Gem itemStoneGem = new StoneGem();
	@ModItem
	public static Gem itemTimeSpaceGem = new TimeSpaceGem();
	@ModItem
	public static Gem itemPathfinderGem = new PathfinderGem();
	@ModItem
	public static Gem itemEtherealGem = new EtherealGem();
	@ModItem
	public static Gem itemGuardianGem = new GuardianGem();
	@ModItem
	public static Gem itemFireGem = new FireGem();
	
	@ModItem
	public static Item itemStoneCraftable = new StoneCraftable();
	@ModItem
	public static Item itemPathfinderCraftable = new PathfinderCraftable();
	@ModItem
	public static Item itemEnderShard = new EnderShard();
	
	@ModPotion
	public static class Potions {
		public static BlargsPotion warpSickness = new BlargsPotion(new ResourceLocation(ModInfo.ID, "warpsickness"), "potion.warpingsickness");
		public static BlargsPotion enderShardWarp = new BlargsPotion(new ResourceLocation(ModInfo.ID, "endershard"), "potion.endershard");
	}
	
	@ModSound
	public static class Sounds {
		public static SoundEvent swoosh = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "swoosh"));
		public static SoundEvent teleport = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "teleport"));
	}

	@ModRecipe
	public static class SmeltingRecipes {
		public static IBlargRecipe blastedClayPiece = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 0), new ItemStack(ModItems.itemStoneCraftable, 1, 1), 0.1f);
		public static IBlargRecipe blastedCeramicFoundation = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 2), new ItemStack(ModItems.itemStoneCraftable, 1, 3), 1f);
		public static IBlargRecipe hyperInfusedMass = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 6), new ItemStack(ModItems.itemStoneCraftable, 1, 7), 1f);
		public static IBlargRecipe refinedObsidian = new SmeltingRecipe(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModItems.itemStoneCraftable, 1, 8), 0.4f);
		public static IBlargRecipe hardenedObsidianClump = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 9), new ItemStack(ModItems.itemStoneCraftable, 1, 10), 0.7f);
		public static IBlargRecipe strengthenedDiamondLattice = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 11), new ItemStack(ModItems.itemStoneCraftable, 1, 12), 1f);
		public static IBlargRecipe heatedCrystallineLatticeStructure = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 13), new ItemStack(ModItems.itemStoneCraftable, 1, 14), 1f);
		public static IBlargRecipe stoneGem = new SmeltingRecipe(new ItemStack(ModItems.itemStoneCraftable, 1, 15), new ItemStack(ModItems.itemStoneGem), 1f);
	}
	
	@ModSprite
	public static class Sprites {
		public static ResourceLocation stoneSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-stone");
		public static ResourceLocation pathfinderSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-pathfinder");
		public static ResourceLocation timeSpaceSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-time-and-space");
		public static ResourceLocation fireSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-fire");
		public static ResourceLocation guardianSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-guardian");
		public static ResourceLocation etherealSlotBackground = new ResourceLocation(ModInfo.ID, "items/slot-gem-ethereal");
	}
}
