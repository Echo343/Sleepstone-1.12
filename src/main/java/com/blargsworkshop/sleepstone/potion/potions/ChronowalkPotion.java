package com.blargsworkshop.sleepstone.potion.potions;

import com.blargsworkshop.engine.potion.BlargsPotion;
import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ChronowalkPotion extends BlargsPotion {

	private static final int UPDATE_DURATION = 20 * 10;
	public static final float CHRONO_STEPHEIGHT = 1.0f;
	public static final float NORMAL_STEPHEIGHT = 0.6f;

	public ChronowalkPotion(ResourceLocation registryName, String messageKey) {
		super(registryName, messageKey);
		registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.22499999403953552D, 2);
		setBeneficial();
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		if (Utils.isClient(entity.getEntityWorld())) {
			// ticksExisted is used bc this potion is applied at infinite duration.
			if (entity instanceof EntityPlayer && entity.ticksExisted % UPDATE_DURATION == 0) {
				((EntityPlayer) entity).stepHeight = CHRONO_STEPHEIGHT;
			}
		}
	}
	
	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

}
