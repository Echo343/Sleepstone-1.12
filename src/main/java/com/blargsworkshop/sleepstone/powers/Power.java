package com.blargsworkshop.sleepstone.powers;

import com.blargsworkshop.sleepstone.items.stone.Slots;

public enum Power {
	VENOM_IMMUNITY(Slots.Stone, Slots.Stone),
	ETHEREAL_FEET(Slots.Stone, Slots.Stone),
	ROCK_BARRIER(Slots.Stone, Slots.Stone),
	PRECOGNITION(Slots.Stone, Slots.Stone),
	TEMPORAL_AID(Slots.Stone, Slots.Stone),
	HELLJUMPER(Slots.Stone, Slots.Stone),
	IRON_STOMACH(Slots.Stone, Slots.Stone),
	PHANTOM_TORCH(Slots.Stone, Slots.Stone),
	WINDWALKER(Slots.Stone, Slots.Stone);
	
	private Slots firstGem;
	private Slots secondGem;
	
	Power(Slots firstGemSlot, Slots secondGemSlot) {
		this.firstGem = firstGemSlot;
		this.secondGem = secondGemSlot;
	}
	
	public Slots getFirstGemSlot() {
		return this.firstGem;
	}
	
	public Slots getSecondGemSlot() {
		return this.secondGem;
	}
}
