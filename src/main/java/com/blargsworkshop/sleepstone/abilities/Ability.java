package com.blargsworkshop.sleepstone.abilities;

import com.blargsworkshop.sleepstone.items.stone.inventory.gui.StoneSlotType;

public enum Ability {
	VENOM_IMMUNITY(StoneSlotType.GUARDIAN_1, StoneSlotType.MONK_1),
	ETHEREAL_FEET(StoneSlotType.GUARDIAN_2, StoneSlotType.ETHEREAL_1),
	ROCK_BARRIER(StoneSlotType.GUARDIAN_3, StoneSlotType.ELEMENTAL_1),
	PRECOGNITION(StoneSlotType.TIMESPACE_1, StoneSlotType.MONK_2),
	TEMPORAL_AID(StoneSlotType.TIMESPACE_2, StoneSlotType.ETHEREAL_2),
	HELLJUMPER(StoneSlotType.TIMESPACE_3, StoneSlotType.ELEMENTAL_2),
	IRON_STOMACH(StoneSlotType.PATHFINDER_1, StoneSlotType.MONK_3),
	PHANTOM_TORCH(StoneSlotType.PATHFINDER_2, StoneSlotType.ETHEREAL_3),
	WINDWALKER(StoneSlotType.PATHFINDER_3, StoneSlotType.ELEMENTAL_3);
	
	private StoneSlotType firstGem;
	private StoneSlotType secondGem;
	
	Ability(StoneSlotType firstGemSlot, StoneSlotType secondGemSlot) {
		this.firstGem = firstGemSlot;
		this.secondGem = secondGemSlot;
	}
	
	public StoneSlotType getFirstGemSlot() {
		return this.firstGem;
	}
	
	public StoneSlotType getSecondGemSlot() {
		return this.secondGem;
	}
}
