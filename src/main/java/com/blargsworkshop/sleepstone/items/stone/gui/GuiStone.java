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
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage.Command;
import com.blargsworkshop.sleepstone.network.packets.toserver.OpenGuiMessage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	protected static enum Button {
		VENOM_IMMUNITY(Ability.VENOM_IMMUNITY),
		ETHEREAL_FEET(Ability.ETHEREAL_FEET),
		ROCK_BARRIER(Ability.ROCK_BARRIER),
		PRECOGNITION(Ability.PRECOGNITION),
		TEMPORAL_AID(Ability.TEMPORAL_AID),
		HELLJUMPER(Ability.HELLJUMPER),
		IRON_STOMACH(Ability.IRON_STOMACH),
		PHANTOM_TORCH(Ability.PHANTOM_TORCH),
		WINDWALKER(Ability.WINDWALKER),
		INV;
		
		private Ability ability = null;
		
		Button() {
		}
		
		Button(Ability ability) {
			this.ability = ability;
		}
		
		public Ability getAbility() {
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
	private IAbilityStatus props;
	
	private TooltipContainer buttonTooltips = new TooltipContainer();
	
	public GuiStone(EntityPlayer player, StoneInventory stoneInventory) {
		this.inventory = stoneInventory;
		this.player = player;
		this.props = player.getCapability(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, null);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}
	
	private boolean shouldShowButton(Button button) {
		return inventory.hasGemInSlot(button.getAbility());
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
		
		if (shouldShowButton(Button.VENOM_IMMUNITY)) {
			ToggleButton button = new ToggleButton(Button.VENOM_IMMUNITY, firstColumn, firstRow, Utils.localize("text.guistone.stone_button"));
			button.setState(props.getAbility(Button.VENOM_IMMUNITY.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.ETHEREAL_FEET)) {
			ToggleButton button = new ToggleButton(Button.ETHEREAL_FEET, firstColumn, secondRow, Utils.localize("text.guistone.stone_ethereal_button"));
			button.setState(props.getAbility(Button.ETHEREAL_FEET.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_ethereal_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.ROCK_BARRIER)) {
			BasicButton button = new BasicButton(Button.ROCK_BARRIER, firstColumn, thirdRow, Utils.localize("text.guistone.stone_guardian_button"));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_guardian_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.PRECOGNITION)) {
			ToggleButton button = new ToggleButton(Button.PRECOGNITION, firstColumn, fourthRow, Utils.localize("text.guistone.stone_fire_button"));
			button.setState(props.getAbility(Button.PRECOGNITION.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.stone_fire_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.TEMPORAL_AID)) {
			ToggleButton button = new ToggleButton(Button.TEMPORAL_AID, secondColumn, firstRow, Utils.localize("text.guistone.timespace_button"));
			button.setState(props.getAbility(Button.TEMPORAL_AID.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.HELLJUMPER)) {
			ToggleButton button = new ToggleButton(Button.HELLJUMPER, secondColumn, secondRow, Utils.localize("text.guistone.timespace_ethereal_button"));
			button.setState(props.getAbility(Button.HELLJUMPER.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_ethereal_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.IRON_STOMACH)) {
			ToggleButton button = new ToggleButton(Button.IRON_STOMACH, secondColumn, thirdRow, Utils.localize("text.guistone.timespace_guardian_button"));
			button.setState(props.getAbility(Button.IRON_STOMACH.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_guardian_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.PHANTOM_TORCH)) {
			ToggleButton button = new ToggleButton(Button.PHANTOM_TORCH, secondColumn, fourthRow, Utils.localize("text.guistone.timespace_fire_button"));
			button.setState(props.getAbility(Button.PHANTOM_TORCH.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.timespace_fire_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		if (shouldShowButton(Button.WINDWALKER)) {
			ToggleButton button = new ToggleButton(Button.WINDWALKER, thirdColumn, firstRow, Utils.localize("text.guistone.pathfinder_button"));
			button.setState(props.getAbility(Button.WINDWALKER.getAbility()));
			Tooltip tooltip = new Tooltip(button, Utils.localize("text.guistone.pathfinder_tooltip"));
			buttonTooltips.add(tooltip);
			buttonList.add(button);
		}
		
		GuiButton invButton = new BasicButton(Button.INV, left + xSize - 40, top + ySize - 30, 20, Utils.localize("text.guistone.inv"));
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
		Button btn = (Button)((BasicButton)button).getButtonType();
				
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
