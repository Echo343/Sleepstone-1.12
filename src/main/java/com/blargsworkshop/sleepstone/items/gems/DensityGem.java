package com.blargsworkshop.sleepstone.items.gems;

/***
 * A gem of unusual density.  It feels heavier than it should.
 * @author Echo343
 * 
 * Craftable from:
 *   Obsidian -craft-> Obsidian Clump -furnace-> Hardened Obsidian Clump
 *   Diamond Blocks -craft-> Lattice Diamond Encased Clump -furnace-> Hardened Crystallized Encased Structure
 *   Sand, Clay -craft-> Molded Ceramic Shield Ball -furnace-> Blasted Ceramic Encased Orb
 *   Redstone blocks -craft-> Laced Redstone Encased Orb -furnace-> Infused Redstone Orb
 *   Gold blocks -craft-> Densely Packed Orb -furnace-> Density Gem
 *   
 *   Smelt with a stack of coal between each one.
 */
public class DensityGem extends Gem {

	private static final String UNLOCALIZEDNAME = "densitygem";
	private static final String TEXTURE = "sleepstonemod:gem-density";
	
	public DensityGem() {
		super(UNLOCALIZEDNAME, TEXTURE);
	}

}
