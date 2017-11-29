package com.blargsworkshop.sleepstone;

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

public class ModItems {
	public static CreativeTabs tabSleepstone = RegisterModComponents.getCreativeTab();
	
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
	
	public static class Potions {
		public static BlargsPotion warpSickness = new BlargsPotion(new ResourceLocation(ModInfo.ID, "warpsickness"), "potion.warpingsickness");
		public static BlargsPotion enderShardWarp = new BlargsPotion(new ResourceLocation(ModInfo.ID, "endershard"), "potion.endershard");
	}
	
	public static class Sounds {
		public static SoundEvent swoosh = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "swoosh"));
		public static SoundEvent teleport = new BlargsSoundEvent(new ResourceLocation(ModInfo.ID, "teleport"));
	}
}
