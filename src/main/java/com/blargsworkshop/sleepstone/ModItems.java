package com.blargsworkshop.sleepstone;

import com.blargsworkshop.sleepstone.annotations.ModItem;
import com.blargsworkshop.sleepstone.annotations.ModPotion;
import com.blargsworkshop.sleepstone.annotations.ModSound;
import com.blargsworkshop.sleepstone.events.RegisterModComponents;
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
import com.blargsworkshop.sleepstone.sound.BlargsSoundEvent;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * All the items in the mod.
 * They must be initialized here since I'm using Mod.EventBusSubscriber(modid = ModInfo.ID) to register the event handler.
 * A constructor would be too late in the process.
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
}
