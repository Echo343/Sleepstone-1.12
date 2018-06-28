package com.blargsworkshop.engine.annotations.render.tesr;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TESRMap<T extends TileEntity> {
	private final Class<T> tileEntityClass; 
	private final TileEntitySpecialRenderer<? super T> specialRenderer;
	
	public TESRMap(Class<T> teClass, TileEntitySpecialRenderer<? super T> specialRenderer) {
		tileEntityClass = teClass;
		this.specialRenderer = specialRenderer;
	}

	public Class<T> getTileEntityClass() {
		return tileEntityClass;
	}

	public TileEntitySpecialRenderer<? super T> getSpecialRenderer() {
		return specialRenderer;
	}	
}
