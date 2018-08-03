package com.blargsworkshop.sleepstone.abilities.phantom_torch;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.BlockTorch;

public class PhantomTorch extends BlockTorch {
	private static final String UNLOCALIZED_NAME = "phantomtorch";
	private static final String REGISTRY_NAME = "phantomtorch";

	public PhantomTorch() {
		super();
		this.setUnlocalizedName(UNLOCALIZED_NAME);
		this.setRegistryName(REGISTRY_NAME);
		this.setCreativeTab(ModItems.tabSleepstone);
	}

}
