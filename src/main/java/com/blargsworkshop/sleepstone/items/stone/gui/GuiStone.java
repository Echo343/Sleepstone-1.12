package com.blargsworkshop.sleepstone.items.stone.gui;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModInfo.DEBUG;
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.extended_properties.ExtendedPlayer;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.gui.buttons.BasicButton;
import com.blargsworkshop.sleepstone.gui.buttons.ToggleButton;
import com.blargsworkshop.sleepstone.gui.buttons.TooltipButton;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.items.stone.StoneInventory;
import com.blargsworkshop.sleepstone.network.PacketDispatcher;
import com.blargsworkshop.sleepstone.network.bidirectional.SyncAllPlayerPropsMessage;
import com.blargsworkshop.sleepstone.network.server.OpenGuiMessage;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	public static enum Buttons {
		Warp,
		Stone,
		Pathfinder,
		Inv
	}
	
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/GuiStone.png");
	
	private final int xSize = 248;
	private final int ySize = 166;
	private StoneInventory inventory;
	private EntityPlayer player;
	private ExtendedPlayer props;
	
	public GuiStone(EntityPlayer player, StoneInventory stoneInventory) {
		this.inventory = stoneInventory;
		this.player = player;
		this.props = ExtendedPlayer.get(player);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}
	
	@SuppressWarnings("unchecked")
	protected void initButtons() {
		int left = (this.width - xSize) / 2;
		int top = (this.height - ySize) / 2;
		
		if (inventory.hasGemInSlot(Slots.Stone)) {
			ToggleButton stoneButton = new ToggleButton(Buttons.Stone, left + 4, top + 4, localize("text.guistone.stone_button"), localize("text.guistone.stone_tooltip"));
			if (props != null) {
				stoneButton.setState(props.getNoFallDamage());
			}
			else {
				SleepstoneMod.debug("Error getting props", DEBUG.DETAIL, player);
			}
			this.buttonList.add(stoneButton);
		}
		if (inventory.hasGemInSlot(Slots.Pathfinder)) {
			ToggleButton pathfinderButton = new ToggleButton(Buttons.Pathfinder, left + 124, top + 28, localize("text.guistone.pathfinder_button"), localize("text.guistone.pathfinder_tooltip"));
			this.buttonList.add(pathfinderButton);
		}
		
		GuiButton warpButton = new BasicButton(Buttons.Warp, (this.width - 70) / 2, (this.height - 20) / 2, 70, localize("text.guistone.warp"));
		GuiButton invButton = new BasicButton(Buttons.Inv, left + xSize - 24, top + 4, 20, localize("text.guistone.inv"));
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
		
		//Draw Tooltips last so they are on top.
		for (Object button : this.buttonList) {
			if (button instanceof TooltipButton) {
				((TooltipButton)button).drawTooltip(this.mc, mouseX, mouseY);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch ((Buttons)((BasicButton)button).getButtonType()) {
		case Warp:
			// TODO send command packet.
//			NetworkHandler.networkWrapper.sendToServer(new BasicMessage(Commands.WARP));
			this.mc.setIngameFocus();
			break;
		case Inv:
			PacketDispatcher.sendToServer(new OpenGuiMessage(GuiEnum.STONE_INVENTORY));
			break;
		case Stone:
		case Pathfinder:
			ToggleButton btn = (ToggleButton) button;
			btn.toggle();
			props.setNoFallDamage(btn.isOn(), false);
			PacketDispatcher.sendToServer(new SyncAllPlayerPropsMessage(this.player));
			break;
		default:
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
}
