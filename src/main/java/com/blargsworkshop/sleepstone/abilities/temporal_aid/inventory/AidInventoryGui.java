package com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class AidInventoryGui extends GuiContainer
{    
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/GuiInventoryStone.png");
	
	public AidInventoryGui(AidInventoryContainer containerItem)
	{
		super(containerItem);
		this.xSize = 175;
		this.ySize = 222;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void initGui() {
		super.initGui();
	}
	
	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String strSleepstone = Utils.localize("text.guistoneinventory.sleepstone");
		String strContainer = Utils.localize("container.inventory");
		this.fontRenderer.drawString(strSleepstone, (this.xSize - this.fontRenderer.getStringWidth(strSleepstone)) / 2, 5, 4210752);
		this.fontRenderer.drawString(strContainer, 8, this.ySize - 96 + 2, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float renderParitalTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(backgroundImage);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
}