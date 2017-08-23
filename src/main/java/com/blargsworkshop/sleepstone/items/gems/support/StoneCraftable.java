package com.blargsworkshop.sleepstone.items.gems.support;

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
 * item.stonecraftable_9.name=Refined Obsidian Clump
 * item.stonecraftable_10.name=Hardened Obsidian Clump
 * item.stonecraftable_11.name=Brittle Diamond Lattice
 * item.stonecraftable_12.name=Strengthened Diamond Lattice
 * item.stonecraftable_13.name=Crystalline Lattice Structure
 * item.stonecraftable_14.name=Heated Crystalline Lattice Structure
 * item.stonecraftable_15.name=Radial Empowered Orb
 * 
 * @author Echo343
 *
 */
public class StoneCraftable extends BaseCraftable {
	
	private static final String UNLOCALIZED_NAME = "stonecraftable"; 
	private static final int NUMBER_OF_CRAFTABLES = 16;

	public StoneCraftable() {
		super(UNLOCALIZED_NAME, NUMBER_OF_CRAFTABLES);
	}
}
