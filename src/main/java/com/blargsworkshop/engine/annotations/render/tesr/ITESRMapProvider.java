package com.blargsworkshop.engine.annotations.render.tesr;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Have your tileEntity implement this interface and annotate your TE with @TESR in
 * your ModItems class to have your TESR registered automatically.
 *
 * @param <T> This parameter is the type of your tileEntity
 */
@SideOnly(Side.CLIENT)
public interface ITESRMapProvider<T extends TileEntity> {
	public TESRMap<T> getTESRMap(); 
}
