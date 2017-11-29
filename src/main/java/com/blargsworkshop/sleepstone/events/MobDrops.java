package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class MobDrops {
	public static void handleGlobalGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.getEntityLiving();
		if (	mob instanceof EntityMob ||
				mob instanceof EntitySlime && ((EntitySlime) mob).getSlimeSize() == 1 ||
				mob instanceof EntityFlying) {
			double dropChance = Math.random();
			// 0.5% drop chance
			if (dropChance <= 0.005) {
				// Pick a random gem
				int gemIndex = (int) (Math.random() * Slots.values().length);
				ItemStack loot = new ItemStack(Slots.values()[gemIndex].getItem());
				event.getDrops().add(new EntityItem(mob.getEntityWorld(), mob.posX, mob.posY, mob.posZ, loot));
			}
		}
	}

	public static void handleFireGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.getEntityLiving();
		// Mobs in the nether only
		if (mob.dimension == DimensionType.NETHER.getId() &&
			   (mob instanceof EntityMob ||
				mob instanceof EntitySlime && ((EntitySlime) mob).getSlimeSize() == 3 ||
				mob instanceof EntityFlying)) {
			double dropChance = Math.random();
			// 3% drop chance
			if (dropChance <= 0.03) {
				ItemStack fireGem = new ItemStack(ModItems.itemFireGem);
				event.getDrops().add(new EntityItem(mob.getEntityWorld(), mob.posX, mob.posY, mob.posZ, fireGem));
			}
		}
	}

	public static void handleEtherealGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.getEntityLiving();
		if (mob instanceof EntitySpider && mob.isPotionActive(Potion.getPotionFromResourceLocation("minecraft:invisibility"))) {		
			ItemStack etherealGem = new ItemStack(ModItems.itemEtherealGem);		
			event.getDrops().add(new EntityItem(mob.getEntityWorld(), mob.posX, mob.posY, mob.posZ, etherealGem));
		}
	}
	
	public static void handleEnderShardDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.getEntityLiving();
		if (	mob instanceof EntityEnderman ||
				mob instanceof EntityEndermite) {
			double dropChance = Math.random();
			// ~7% drop chance (1/15)
			if (dropChance <= 0.067) {
				ItemStack enderShard = new ItemStack(ModItems.itemEnderShard);
				event.getDrops().add(new EntityItem(mob.getEntityWorld(), mob.posX, mob.posY, mob.posZ, enderShard));
			}
		}		
	}
}
