package com.blargsworkshop.sleepstone;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.annotations.ModBlock;
import com.blargsworkshop.engine.annotations.ModItem;
import com.blargsworkshop.engine.annotations.ModPotion;
import com.blargsworkshop.engine.annotations.ModRecipe;
import com.blargsworkshop.engine.annotations.ModSound;
import com.blargsworkshop.engine.annotations.ModSprite;
import com.blargsworkshop.engine.annotations.ModTileEntity;
import com.blargsworkshop.engine.annotations.render.NoBlockstate;
import com.blargsworkshop.engine.annotations.render.TEISR;
import com.blargsworkshop.engine.annotations.render.TESR;
import com.blargsworkshop.engine.event.RegisterModComponents;
import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.recipe.IBlargRecipe;
import com.blargsworkshop.engine.recipe.SmeltingRecipe;
import com.blargsworkshop.engine.sound.BlargsSoundEvent;
import com.blargsworkshop.sleepstone.abilities.iron_stomach.FoodSaturationPotion;
import com.blargsworkshop.sleepstone.abilities.windwalker.WindwalkerPotion;
import com.blargsworkshop.sleepstone.items.airmattress.AirMattressBlock;
import com.blargsworkshop.sleepstone.items.airmattress.AirMattressItem;
import com.blargsworkshop.sleepstone.items.airmattress.AirMattressItemStackRenderer;
import com.blargsworkshop.sleepstone.items.airmattress.AirMattressTileEntity;
import com.blargsworkshop.sleepstone.items.airmattress.TileEntityAirMattressRenderer;
import com.blargsworkshop.sleepstone.items.endershard.EnderShard;
import com.blargsworkshop.sleepstone.items.gems.ElementalGem;
import com.blargsworkshop.sleepstone.items.gems.EtherealGem;
import com.blargsworkshop.sleepstone.items.gems.Gem;
import com.blargsworkshop.sleepstone.items.gems.GuardianGem;
import com.blargsworkshop.sleepstone.items.gems.MonkGem;
import com.blargsworkshop.sleepstone.items.gems.PathfinderGem;
import com.blargsworkshop.sleepstone.items.gems.TimeSpaceGem;
import com.blargsworkshop.sleepstone.items.gems.mats.FireSeed;
import com.blargsworkshop.sleepstone.items.gems.mats.MonkCraftable;
import com.blargsworkshop.sleepstone.items.gems.mats.PathfinderCraftable;
import com.blargsworkshop.sleepstone.items.gems.mats.WaterSeed;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * All the items in the mod.
 */
public class ModItems implements IModItems{
	public static CreativeTabs tabSleepstone = RegisterModComponents.createCreativeTab(ModInfo.CREATIVE_TAB_SLEEPSTONE, () -> ModItems.itemSleepstone);
	
	@ModItem
	public static Item itemSleepstone = new Sleepstone();
	
	@ModItem
	public static Gem itemMonkGem = new MonkGem();
	@ModItem
	public static Gem itemTimeSpaceGem = new TimeSpaceGem();
	@ModItem
	public static Gem itemPathfinderGem = new PathfinderGem();
	@ModItem
	public static Gem itemEtherealGem = new EtherealGem();
	@ModItem
	public static Gem itemGuardianGem = new GuardianGem();
	@ModItem
	public static Gem itemElementalGem = new ElementalGem();
	
	@ModItem
	public static Item itemMonkCraftable = new MonkCraftable();
	@ModItem
	public static Item itemPathfinderCraftable = new PathfinderCraftable();
	@ModItem
	public static Item itemFireSeed = new FireSeed();
	@ModItem
	public static Item itemWaterSeed = new WaterSeed();
	@ModItem
	public static Item itemEnderShard = new EnderShard();
	
	@ModItem
	public static class Items {
		@TEISR(AirMattressItemStackRenderer.class)
		public static AirMattressItem airMattress = new AirMattressItem();
	}
	
	@ModBlock
	public static class Blocks {
		@NoBlockstate
		public static AirMattressBlock airMattress = new AirMattressBlock();
	}
	
	@ModTileEntity
	public static class TEs {
		@TESR(TileEntityAirMattressRenderer.class)
		public static AirMattressTileEntity airMattress = new AirMattressTileEntity();
	}
	
	@ModPotion
	public static class Potions {
		public static BlargsPotion warpSickness = new BlargsPotion(new ResourceLocation(ModInfo.ID, "warpsickness"), "potion.warpingsickness");
		public static BlargsPotion enderShardWarp = new BlargsPotion(new ResourceLocation(ModInfo.ID, "endershard"), "potion.endershard");
		public static BlargsPotion foodSaturation = new FoodSaturationPotion(new ResourceLocation(ModInfo.ID, "foodsaturation"), "potion.foodsaturation");
		public static BlargsPotion windwalker = new WindwalkerPotion(new ResourceLocation(ModInfo.ID, "windwalker"), "potion.windwalker");
	}
	
	@ModSound
	public static class Sounds {
		public static SoundEvent swoosh = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "swoosh"));
		public static SoundEvent teleport = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "teleport"));
	}

	@ModRecipe
	public static class SmeltingRecipes {
		public static IBlargRecipe blastedClayPiece = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 0), new ItemStack(ModItems.itemMonkCraftable, 1, 1), 0.1f);
		public static IBlargRecipe blastedCeramicFoundation = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 2), new ItemStack(ModItems.itemMonkCraftable, 1, 3), 1f);
		public static IBlargRecipe hyperInfusedMass = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 6), new ItemStack(ModItems.itemMonkCraftable, 1, 7), 1f);
		public static IBlargRecipe refinedObsidian = new SmeltingRecipe(new ItemStack(net.minecraft.init.Blocks.OBSIDIAN), new ItemStack(ModItems.itemMonkCraftable, 1, 8), 0.4f);
		public static IBlargRecipe hardenedObsidianClump = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 10), new ItemStack(ModItems.itemMonkCraftable, 1, 11), 0.7f);
		public static IBlargRecipe strengthenedDiamondLattice = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 12), new ItemStack(ModItems.itemMonkCraftable, 1, 13), 1f);
		public static IBlargRecipe heatedCrystallineLatticeStructure = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 14), new ItemStack(ModItems.itemMonkCraftable, 1, 15), 1f);
		public static IBlargRecipe monkGem = new SmeltingRecipe(new ItemStack(ModItems.itemMonkCraftable, 1, 16), new ItemStack(ModItems.itemMonkGem), 1f);
	}
	
	@ModSprite
	public static class Sprites {
		public static ResourceLocation monkSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-monk");
		public static ResourceLocation pathfinderSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-pathfinder");
		public static ResourceLocation timeSpaceSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-time_space");
		public static ResourceLocation elementalSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-elemental");
		public static ResourceLocation guardianSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-guardian");
		public static ResourceLocation etherealSlotBackground = new ResourceLocation(ModInfo.ID, "items/slotbackground-gem-ethereal");
	}
}
