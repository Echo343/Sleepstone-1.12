package com.blargsworkshop.sleepstone.items.gems.mats;

import com.blargsworkshop.sleepstone.items.BaseSubtype;

/**
 * item.stonecraftable_0.name=Hardened Clay Piece
 * item.stonecraftable_1.name=Blasted Clay Piece
 * item.stonecraftable_2.name=Ceramic Foundation
 * item.stonecraftable_3.name=Blasted Ceramic Foundation
 * item.stonecraftable_4.name=Condensed Redstone Block
 * item.stonecraftable_5.name=Hyper Lattice Redstone Block
 * item.stonecraftable_6.name=Hyper Lattice Redstone Mass
 * item.stonecraftable_7.name=Hyper Infused Mass
 * item.stonecraftable_8.name=Refined Obsidian
 * item.stonecraftable_9.name=Refined Obsidian Mass
 * item.stonecraftable_10.name=Refined Obsidian Clump
 * item.stonecraftable_11.name=Hardened Obsidian Clump
 * item.stonecraftable_12.name=Brittle Diamond Lattice
 * item.stonecraftable_13.name=Strengthened Diamond Lattice
 * item.stonecraftable_14.name=Crystalline Lattice Structure
 * item.stonecraftable_15.name=Heated Crystalline Lattice Structure
 * item.stonecraftable_16.name=Radial Empowered Orb
 * 
 * @author Echo343
 *
 */
public class MonkCraftable extends BaseSubtype {
	
	private static final String UNLOCALIZED_NAME = "craftable_monk";
	private static final String REGISTRY_NAME = "craftable_monk";
	private static final int NUMBER_OF_CRAFTABLES = 17;

	public MonkCraftable() {
		super(UNLOCALIZED_NAME, REGISTRY_NAME, NUMBER_OF_CRAFTABLES);
	}
}
