package com.blargsworkshop.sleepstone.abilities.temporal_aid.gui;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.engine.gui.buttons.BasicButton;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.OpenAidGuiMessage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PlayerSelectionGui extends GuiScreen {
		
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/guistone.png");
	
	private static final int X_SIZE = 248;
	private static final int Y_SIZE = 166;
	private static final int TOP_MARGIN = 0;
	private static final int VERTICAL_SPACING = 4;
	private static final int BUTTON_SCREEN_WIDTH = BasicButton.DEFAULT_WIDTH;
	private static final int EFFECTIVE_BUTTON_HEIGHT = BasicButton.DEFAULT_HEIGHT + VERTICAL_SPACING;
//	private EntityPlayer player;
	
	private Map<Integer, UUID> buttonMap = new HashMap<>();
	
	
	public PlayerSelectionGui(EntityPlayer player) {
//		this.player = player;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}
		
	protected void initButtons() {
		int i = 0;
		int btnLeft = (this.width - BUTTON_SCREEN_WIDTH) / 2;
		int top = (this.height - Y_SIZE) / 2;
		
		int firstRow = top + TOP_MARGIN;
		int firstColumn = btnLeft;
		
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getConnection().getPlayerInfoMap();
		Iterator<NetworkPlayerInfo> iter = players.iterator();
		while (iter.hasNext()) {
			NetworkPlayerInfo playerInfo = iter.next();
			BasicButton button = new BasicButton(i, firstColumn, firstRow + (i * EFFECTIVE_BUTTON_HEIGHT), playerInfo.getGameProfile().getName());
			this.addButton(button);
			buttonMap.put(i, playerInfo.getGameProfile().getId());
			i++;
		};
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float renderPartialTicks) {
		super.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(backgroundImage);
		int x = (this.width - X_SIZE) / 2;
		int y = (this.height - Y_SIZE) / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x, y, 0, 0, X_SIZE, Y_SIZE);
		super.drawScreen(mouseX, mouseY, renderPartialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		BasicButton btn = button instanceof BasicButton ? (BasicButton) button : null;
		if (btn != null) {
			NetworkOverlord.get(ModInfo.ID).sendToServer(new OpenAidGuiMessage(buttonMap.get(btn.getId())));
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
		try {
			super.keyTyped(c, i);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
