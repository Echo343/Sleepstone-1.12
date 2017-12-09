package com.blargsworkshop.engine.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipe implements IBlargRecipe {
	
	private ItemStack input = ItemStack.EMPTY;
	private ItemStack output = ItemStack.EMPTY;
	private float xp = 0f;
	

	public SmeltingRecipe(ItemStack input, ItemStack output, float xp) {
		if (input != null) {
			this.input = input;
		}
		if (output != null) {
			this.output = output;
		}
		this.xp = xp;
	}

	@Override
	public void register() {
		GameRegistry.addSmelting(getInput(), getOutput(), getXp());
	}
	
	@Override
	public String getOutputName() {
		return getOutput().getDisplayName();
	}

	protected ItemStack getInput() {
		return input;
	}

	protected ItemStack getOutput() {
		return output;
	}
	protected float getXp() {
		return xp;
	}


}
