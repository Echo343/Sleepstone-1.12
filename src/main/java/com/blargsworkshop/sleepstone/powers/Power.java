package com.blargsworkshop.sleepstone.powers;

import com.blargsworkshop.sleepstone.items.stone.Slots;

public enum Power {
	VENOM_IMMUNITY(Slots.GUARDIAN_1, Slots.MONK_1),
	ETHEREAL_FEET(Slots.GUARDIAN_2, Slots.ETHEREAL_1),
	ROCK_BARRIER(Slots.GUARDIAN_3, Slots.ELEMENTAL_1),
	PRECOGNITION(Slots.TIMESPACE_1, Slots.MONK_2),
	TEMPORAL_AID(Slots.TIMESPACE_2, Slots.ETHEREAL_2),
	HELLJUMPER(Slots.TIMESPACE_3, Slots.ELEMENTAL_2),
	IRON_STOMACH(Slots.PATHFINDER_1, Slots.MONK_3),
	PHANTOM_TORCH(Slots.PATHFINDER_2, Slots.ETHEREAL_3),
	WINDWALKER(Slots.PATHFINDER_3, Slots.ELEMENTAL_3);
	
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
