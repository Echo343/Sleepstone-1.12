package com.blargsworkshop.sleepstone.items.stone.gui;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.network.BasicMessage;
import com.blargsworkshop.sleepstone.network.BasicMessage.Commands;
import com.blargsworkshop.sleepstone.network.NetworkHandler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	private static enum Buttons {
		Warp,
		Inv
	}
	
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/GuiStone.png");
	
	private final int xSize = 248;
	private final int ySize = 166;
	
		
	public GuiStone() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		GuiButton warpButton = new StoneGuiButton(Buttons.Warp, (this.width - 70) / 2, (this.height - 20) / 2, 70, 20, "text.guistone.warp");
		GuiButton invButton = new StoneGuiButton(Buttons.Inv, ((this.width - xSize) / 2) + xSize - 24, (this.height - ySize) / 2 + 4, 20, 20, "text.guistone.inv"); // TODO Use an icon of some sort.
		this.buttonList.add(warpButton);
		this.buttonList.add(invButton); // TODO Use an icon of some sort.
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
		switch (((StoneGuiButton)button).getButtonType()) {
		case Warp:
			NetworkHandler.networkWrapper.sendToServer(new BasicMessage(Commands.WARP));
			this.mc.setIngameFocus();
			break;
		case Inv:
			NetworkHandler.networkWrapper.sendToServer(new BasicMessage(Commands.OpenInvGui));
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
	
	private String localize(String messageKey) {
		return LanguageRegistry.instance().getStringLocalization(messageKey);
	}
	
	protected class StoneGuiButton extends GuiButton {

		private final Buttons buttonType;
		
		public StoneGuiButton(Buttons type, int left, int top, int width, int height, String messageKey) {
			super(type.ordinal(), left, top, width, height, localize(messageKey));
			buttonType = type;
		}
		
		public Buttons getButtonType() {
			return buttonType;
		}
	}
}
