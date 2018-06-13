package com.blargsworkshop.sleepstone.items.airmattress;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.BlockBed;

public class AirMattressBlock extends BlockBed {
	private static final String UNLOCALIZED_NAME = "airmattress";
	private static final String REGISTRY_NAME = "airmattressblock";

	public AirMattressBlock() {
		super();
		this.setUnlocalizedName(UNLOCALIZED_NAME);
		this.setRegistryName(REGISTRY_NAME);
		this.setCreativeTab(ModItems.tabSleepstone);
	}

}
