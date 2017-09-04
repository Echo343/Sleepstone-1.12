package com.blargsworkshop.sleepstone.items.stone.gui;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.stone.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiStoneInventory extends GuiContainer
{
    private static final String GUI_TEXTURE = "textures/gui/GuiInventoryStone.png";

	/** ResourceLocation takes 2 parameters: ModId, path to texture at the location:
	 * "src/minecraft/assets/modid/"
	 * 
	 * I have provided a sample texture file that works with this tutorial. Download it
	 * from Forge_Tutorials/textures/gui/
	 */
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, GUI_TEXTURE);

	/** The inventory to render on screen */
	private final StoneInventory inventory;

	public GuiStoneInventory(StoneContainer containerItem)
	{
		super(containerItem);
		this.inventory = containerItem.getInventoryItem();
		this.xSize = 175;
		this.ySize = 222;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void initGui() {
		super.initGui();
		//Add buttons here
	}
	
	/**
	 * Draw loop
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderParitalTicks)
	{
		super.drawScreen(mouseX, mouseY, renderParitalTicks);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
        //TODO use language registery
		// String s = this.inventory.isInventoryNameLocalized() ? this.inventory.getInventoryName() : I18n.getString(this.inventory.getInventoryName());
		String s = this.inventory.getInventoryName();
		this.fontRendererObj.drawString(s, (this.xSize - this.fontRendererObj.getStringWidth(s)) / 2, 5, 4210752);
        // this.fontRendererObj.drawString(I18n.getString("container.inventory"), 26, this.ySize - 96 + 4, 4210752);
        //TODO use language registery
		this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
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