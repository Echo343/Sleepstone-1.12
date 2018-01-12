package com.blargsworkshop.sleepstone.items.stone.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.engine.gui.buttons.BasicButton;
import com.blargsworkshop.engine.gui.buttons.ToggleButton;
import com.blargsworkshop.engine.gui.buttons.Tooltip;
import com.blargsworkshop.engine.gui.buttons.TooltipContainer;
import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.network.NetworkOverlord;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage.Command;
import com.blargsworkshop.sleepstone.network.packets.toserver.OpenGuiMessage;
import com.blargsworkshop.sleepstone.powers.Power;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	protected static enum Buttons {
		VENOM_IMMUNITY(Power.VENOM_IMMUNITY),
		ETHEREAL_FEET(Power.ETHEREAL_FEET),
		ROCK_BARRIER(Power.ROCK_BARRIER),
		PRECOGNITION(Power.PRECOGNITION),
		TEMPORAL_AID(Power.TEMPORAL_AID),
		HELLJUMPER(Power.HELLJUMPER),
		IRON_STOMACH(Power.IRON_STOMACH),
		PHANTOM_TORCH(Power.PHANTOM_TORCH),
		WINDWALKER(Power.WINDWALKER),
		INV;
		
		private Power ability = null;
		
		Buttons() {
		}
		
		Buttons(Power ability) {
			this.ability = ability;
		}
		
		public Power getAbility() {
			return ability;
		}
	}
	
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/guistone.png");
	
	private static final int xSize = 248;
//	private static final int xSize = 376;
	private static final int ySize = 166;
	private static final int leftMargin = 4;
	private static final int topMargin = 4;
	private static final int horizontalSpacing = 4;
	private static final int verticalSpacing = -1;
	private static final int btnScrnWidth = BasicButton.defaultWidth * 3 + leftMargin * 2 + horizontalSpacing * 2;
	private StoneInventory inventory;
	private EntityPlayer player;
	private IAbility props;
	
	private TooltipContainer buttonTooltips = new TooltipContainer();
	
	public GuiStone(EntityPlayer player, StoneInventory stoneInventory) {
		this.inventory = stoneInventory;
		this.player = player;
		this.props = player.getCapability(AbilityProvider.ABILITY_CAPABILITY, null);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}
	
	protected void initButtons() {
		if (props == null) {
			Log.error("Error getting props", player);
			return;
		}
		
		int left = (this.width - xSize) / 2;
		int btnLeft = (this.width - btnScrnWidth) / 2;
		int top = (this.height - ySize) / 2;
		
		int firstColumn = btnLeft + leftMargin;
		int secondColumn = firstColumn + BasicButton.defaultWidth + horizontalSpacing;
		int thirdColumn = secondColumn + BasicButton.defaultWidth + horizontalSpacing;
		
		int firstRow = top + topMargin;
		int secondRow = firstRow + BasicButton.defaultHeight + verticalSpacing;
		int thirdRow = secondRow + BasicButton.defaultHeight + verticalSpacing;
		int fourthRow = thirdRow + BasicButton.defaultHeight + verticalSpacing;
		
		if (inventory.hasGemInSlot(Buttons.VENOM_IMMUNITY.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.VENOM_IMMUNITY, firstColumn, firstRow, Utils.localize("text.guistone.stone_button"));
			button.setState(props.getAbility(Buttons.VENOM_IMMUNITY.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.ETHEREAL_FEET.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.ETHEREAL_FEET, firstColumn, secondRow, Utils.localize("text.guistone.stone_ethereal_button"));
			button.setState(props.getAbility(Buttons.ETHEREAL_FEET.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_ethereal_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.ROCK_BARRIER.getAbility())) {
			BasicButton button = new BasicButton(Buttons.ROCK_BARRIER, firstColumn, thirdRow, Utils.localize("text.guistone.stone_guardian_button"));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_guardian_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.PRECOGNITION.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.PRECOGNITION, firstColumn, fourthRow, Utils.localize("text.guistone.stone_fire_button"));
			button.setState(props.getAbility(Buttons.PRECOGNITION.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_fire_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.TEMPORAL_AID.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.TEMPORAL_AID, secondColumn, firstRow, Utils.localize("text.guistone.timespace_button"));
			button.setState(props.getAbility(Buttons.TEMPORAL_AID.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.HELLJUMPER.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.HELLJUMPER, secondColumn, secondRow, Utils.localize("text.guistone.timespace_ethereal_button"));
			button.setState(props.getAbility(Buttons.HELLJUMPER.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_ethereal_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.IRON_STOMACH.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.IRON_STOMACH, secondColumn, thirdRow, Utils.localize("text.guistone.timespace_guardian_button"));
			button.setState(props.getAbility(Buttons.IRON_STOMACH.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_guardian_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.PHANTOM_TORCH.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.PHANTOM_TORCH, secondColumn, fourthRow, Utils.localize("text.guistone.timespace_fire_button"));
			button.setState(props.getAbility(Buttons.PHANTOM_TORCH.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_fire_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Buttons.WINDWALKER.getAbility())) {
			ToggleButton button = new ToggleButton(Buttons.WINDWALKER, thirdColumn, firstRow, Utils.localize("text.guistone.pathfinder_button"));
			button.setState(props.getAbility(Buttons.WINDWALKER.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.pathfinder_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		
		GuiButton invButton = new BasicButton(Buttons.INV, left + xSize - 40, top + ySize - 30, 20, Utils.localize("text.guistone.inv"));
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
		buttonTooltips.draw(mc, mouseX, mouseY);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		Buttons btn = (Buttons)((BasicButton)button).getButtonType();
				
		switch (btn) {
		case INV:
			NetworkOverlord.get(ModInfo.ID).sendToServer(new OpenGuiMessage(GuiEnum.STONE_INVENTORY));
			break;
		case ROCK_BARRIER:
			NetworkOverlord.get(ModInfo.ID).sendToServer(new CommandMessage(Command.ROCKWALL));
			player.closeScreen();
			break;
		case ETHEREAL_FEET:
		case HELLJUMPER:
		case IRON_STOMACH:
		case PHANTOM_TORCH:
		case PRECOGNITION:
		case TEMPORAL_AID:
		case VENOM_IMMUNITY:
		case WINDWALKER:
			toggleButton(button);
			props.setAbility(btn.getAbility(), ((ToggleButton) button).isOn());
			break;
		default:
			break;
		}
	}
	
	private void toggleButton(GuiButton btn) {
		if (btn instanceof ToggleButton) {
			((ToggleButton) btn).toggle();
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
