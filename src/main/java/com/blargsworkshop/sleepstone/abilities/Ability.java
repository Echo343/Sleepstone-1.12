package com.blargsworkshop.sleepstone.abilities;

import com.blargsworkshop.sleepstone.items.stone.GemSlot;

public enum Ability {
	VENOM_IMMUNITY(GemSlot.GUARDIAN_1, GemSlot.MONK_1),
	ETHEREAL_FEET(GemSlot.GUARDIAN_2, GemSlot.ETHEREAL_1),
	ROCK_BARRIER(GemSlot.GUARDIAN_3, GemSlot.ELEMENTAL_1),
	PRECOGNITION(GemSlot.TIMESPACE_1, GemSlot.MONK_2),
	TEMPORAL_AID(GemSlot.TIMESPACE_2, GemSlot.ETHEREAL_2),
	HELLJUMPER(GemSlot.TIMESPACE_3, GemSlot.ELEMENTAL_2),
	IRON_STOMACH(GemSlot.PATHFINDER_1, GemSlot.MONK_3),
	PHANTOM_TORCH(GemSlot.PATHFINDER_2, GemSlot.ETHEREAL_3),
	WINDWALKER(GemSlot.PATHFINDER_3, GemSlot.ELEMENTAL_3);
	
	private GemSlot firstGem;
	private GemSlot secondGem;
	
	Ability(GemSlot firstGemSlot, GemSlot secondGemSlot) {
		this.firstGem = firstGemSlot;
		this.secondGem = secondGemSlot;
	}
	
	public GemSlot getFirstGemSlot() {
		return this.firstGem;
	}
	
	public GemSlot getSecondGemSlot() {
		return this.secondGem;
	}
}
