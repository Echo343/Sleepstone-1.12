package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.stone.Slots;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class MobDrops {
	public static void handleGlobalGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.entityLiving;
		if (	mob instanceof EntityMob ||
				mob instanceof EntitySlime && ((EntitySlime) mob).getSlimeSize() == 1 ||
				mob instanceof EntityFlying) {
			double dropChance = Math.random();
			// 0.5% drop chance
			if (dropChance <= 0.005) {
				// Pick a random gem
				int gemIndex = (int) (Math.random() * Slots.values().length);
				ItemStack loot = new ItemStack(Slots.values()[gemIndex].getItem());
				event.drops.add(new EntityItem(mob.worldObj, mob.posX, mob.posY, mob.posZ, loot));
			}
		}
	}

	public static void handleFireGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.entityLiving;
		// Mobs in the nether only
		if (mob.dimension == -1 &&
			   (mob instanceof EntityMob ||
				mob instanceof EntitySlime && ((EntitySlime) mob).getSlimeSize() == 3 ||
				mob instanceof EntityFlying)) {
			double dropChance = Math.random();
			// 3% drop chance
			if (dropChance <= 0.03) {
				ItemStack fireGem = new ItemStack(ModItems.itemFireGem);
				event.drops.add(new EntityItem(mob.worldObj, mob.posX, mob.posY, mob.posZ, fireGem));
			}
		}
	}

	public static void handleEtherealGemDropRates(LivingDropsEvent event) {
		EntityLivingBase mob = event.entityLiving;
		if (mob instanceof EntitySpider && mob.isPotionActive(Potion.invisibility)) {
			ItemStack etherealGem = new ItemStack(ModItems.itemEtherealGem);
			event.drops.add(new EntityItem(mob.worldObj, mob.posX, mob.posY, mob.posZ, etherealGem));
		}
	}
}