package com.blargsworkshop.sleepstone.items.stone.gui;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.network.BasicMessage;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	private static final String GUI_TEXTURE = "textures/gui/GuiStone.png";
	private static final String TEXT_BUTTON_WARP = "text.guistone.warp";
	
	// private EntityPlayer player;
	private int xSize, ySize;
	
	private ResourceLocation backgroundImage;
	
	public GuiStone() {}
	
	public GuiStone(EntityPlayer player) {
		// this.player = player;
		xSize = 247;
		ySize = 165;
		backgroundImage = new ResourceLocation(ModInfo.ID, GUI_TEXTURE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		int buttonWidth = 70;
		int buttonHeight = 20;
		GuiButton warpButton = new GuiButton(1, (this.width - buttonWidth) / 2, (this.height - buttonHeight) / 2, buttonWidth, buttonHeight, LanguageRegistry.instance().getStringLocalization(TEXT_BUTTON_WARP));
		this.buttonList.add(warpButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		super.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(backgroundImage);
		int x = (this.width - xSize) / 2;
		int y = (this.height - ySize) / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		super.drawScreen(mouseX, mouseY, renderPartialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 1:
			SleepstoneMod.networkWrapper.sendToServer(new BasicMessage("Warp"));
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
			break;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (c == 'e' || c == 'E') {
            i = 1;
        }
		super.keyTyped(c, i);
	}
}
