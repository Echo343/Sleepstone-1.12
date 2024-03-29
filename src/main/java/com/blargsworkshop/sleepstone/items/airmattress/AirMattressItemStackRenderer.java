package com.blargsworkshop.sleepstone.items.airmattress;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AirMattressItemStackRenderer extends TileEntityItemStackRenderer {
	
	private final AirMattressTileEntity tile = new AirMattressTileEntity();

	@Override
	public void renderByItem(ItemStack stack, float partialTicks) {
		tile.setItemValues(stack);
		TileEntityRendererDispatcher.instance.render(tile, 0.0D, 0.0D, 0.0D, 0.0F);
	}

}
