package com.blargsworkshop.sleepstone.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;

public class EnderShardPotionEffect extends BlargsPotionEffect {
	private ChunkCoordinates departurePosition;

	public EnderShardPotionEffect(PotionEffect potionEffect) {
		super(potionEffect);
	}

	public EnderShardPotionEffect(int potionId, int duration) {
		super(potionId, duration);
	}

	public EnderShardPotionEffect(int potionId, int duration, int amplifier) {
		super(potionId, duration, amplifier);
	}

	public EnderShardPotionEffect(int potionId, int duration, int amplifier, boolean isAmbient) {
		super(potionId, duration, amplifier, isAmbient);
	}
	
	public void setDepartureCoordinates(ChunkCoordinates position) {
		this.departurePosition = position;
	}
	
	public ChunkCoordinates getDepartureCoordinates() {
		return this.departurePosition;
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {
		Potion p = Potion.potionTypes[this.getPotionID()];
		if (p instanceof EnderShardPotion) {
			((EnderShardPotion) p).onFinishedPotion(entity, this.getDuration(), this.getAmplifier(), this.getDepartureCoordinates());
		}}

}
