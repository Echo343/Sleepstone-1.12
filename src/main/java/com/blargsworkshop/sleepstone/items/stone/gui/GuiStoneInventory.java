package com.blargsworkshop.sleepstone.items.stone.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.items.stone.StoneContainer;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
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
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 0, 4210752);
        // this.fontRendererObj.drawString(I18n.getString("container.inventory"), 26, this.ySize - 96 + 4, 4210752);
        //TODO use language registery
		this.fontRendererObj.drawString("container.inventory", 26, this.ySize - 96 + 4, 4210752);
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

	/**
	 * This renders the player model in standard inventory position (in later versions of Minecraft / Forge, you can
	 * simply call GuiInventory.drawEntityOnScreen directly instead of copying this code)
	 */
//	public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity) {
//		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
//		GL11.glPushMatrix();
//		GL11.glTranslatef(x, y, 50.0F);
//		GL11.glScalef(-scale, scale, scale);
//		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//		float f2 = entity.renderYawOffset;
//		float f3 = entity.rotationYaw;
//		float f4 = entity.rotationPitch;
//		float f5 = entity.prevRotationYawHead;
//		float f6 = entity.rotationYawHead;
//		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
//		RenderHelper.enableStandardItemLighting();
//		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
//		GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
//		entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
//		entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
//		entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
//		entity.rotationYawHead = entity.rotationYaw;
//		entity.prevRotationYawHead = entity.rotationYaw;
//		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
//		RenderManager.instance.playerViewY = 180.0F;
//		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
//		entity.renderYawOffset = f2;
//		entity.rotationYaw = f3;
//		entity.rotationPitch = f4;
//		entity.prevRotationYawHead = f5;
//		entity.rotationYawHead = f6;
//		GL11.glPopMatrix();
//		RenderHelper.disableStandardItemLighting();
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
//		GL11.glDisable(GL11.GL_TEXTURE_2D);
//		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
//	}
}