package com.blargsworkshop.sleepstone.items.gems.support.density;

import java.util.List;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.BaseItem;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * item.densitycraftable_0.name=Obsidian Clump
 * item.densitycraftable_1.name=Hardened Obsidian Clump
 * item.densitycraftable_2.name=Lattice Diamond Encased Clump
 * item.densitycraftable_3.name=Hardened Lattice Diamond Encased Structure
 * item.densitycraftable_4.name=Molded Ceramic Shield Clump
 * item.densitycraftable_5.name=Blasted Ceramic Encased Orb
 * item.densitycraftable_6.name=Laced Redstone Encased Orb
 * item.densitycraftable_7.name=Infused Redstone Orb
 * item.densitycraftable_8.name=Densely Packed Orb
 * 
 * @author pswsadmin
 *
 */
public class DensityCraftable extends BaseItem {
	
	private static final String UNLOCALIZED_NAME = "densitycraftable"; 
	private static final int NUMBER_OF_CRAFTABLES = 9;
	public IIcon[] icons = new IIcon[6];

	public DensityCraftable() {
		super(UNLOCALIZED_NAME);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		for (int i = 0; i < NUMBER_OF_CRAFTABLES; i++) {
			this.icons[i] = reg.registerIcon(ModInfo.ID + ":density-craftable_" + i);
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
