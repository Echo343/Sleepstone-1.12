package com.blargsworkshop.sleepstone.items.gems.support;

import java.util.List;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.BaseItem;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

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
public class StoneCraftable extends BaseItem {
	
	private static final String UNLOCALIZED_NAME = "stonecraftable"; 
	private static final int NUMBER_OF_CRAFTABLES = 16;
	public IIcon[] icons = new IIcon[NUMBER_OF_CRAFTABLES];

	public StoneCraftable() {
		super(UNLOCALIZED_NAME);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
			this.icons[i] = reg.registerIcon(ModInfo.ID + ":stonecraftable_" + i);
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		meta %= NUMBER_OF_CRAFTABLES;
		return this.icons[meta];
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

}
