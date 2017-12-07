package com.blargsworkshop.sleepstone.items.stone.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbility;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.gui.buttons.BasicButton;
import com.blargsworkshop.sleepstone.gui.buttons.ToggleButton;
import com.blargsworkshop.sleepstone.gui.buttons.TooltipButton;
import com.blargsworkshop.sleepstone.items.stone.Slots;
import com.blargsworkshop.sleepstone.items.stone.container.StoneInventory;
import com.blargsworkshop.sleepstone.network.NetworkOverlord;
import com.blargsworkshop.sleepstone.network.server.OpenGuiMessage;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiStone extends GuiScreen {
	
	protected static enum Buttons {
		NoFallDmg(Slots.Stone),
		TimeSpace(Slots.TimeSpace),
		Pathfinder(Slots.Pathfinder),
		StoneEthereal(Slots.StoneEthereal),
		StoneGuardian(Slots.StoneGuardian),
		StoneFire(Slots.StoneFire),
		TimeSpaceEthereal(Slots.TimeSpaceEthereal),
		TimeSpaceGuardian(Slots.TimeSpaceGuardian),
		TimeSpaceFire(Slots.TimeSpaceFire),
		PathfinderEthereal(Slots.PathfinderEthereal),
		PathfinderGuardian(Slots.PathfinderGuardian),
		PathfinderFire(Slots.PathfinderFire),
		Inv;
		
		private Slots slot = null;
		
		Buttons() {
		}
		
		Buttons(Slots slot) {
			this.slot = slot;
		}
		
		public Slots getSlot() {
			return slot;
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
		
		if (inventory.hasGemInSlot(Slots.Stone)) {
			ToggleButton button = new ToggleButton(Buttons.NoFallDmg, firstColumn, firstRow, Utils.localize("text.guistone.stone_button"), Utils.localize("text.guistone.stone_tooltip"));
			button.setState(props.getAbility(Buttons.NoFallDmg.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.StoneEthereal)) {
			ToggleButton button = new ToggleButton(Buttons.StoneEthereal, firstColumn, secondRow, Utils.localize("text.guistone.stone_ethereal_button"), Utils.localize("text.guistone.stone_ethereal_tooltip"));
			button.setState(props.getAbility(Buttons.StoneEthereal.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.StoneGuardian)) {
			ToggleButton button = new ToggleButton(Buttons.StoneGuardian, firstColumn, thirdRow, Utils.localize("text.guistone.stone_guardian_button"), Utils.localize("text.guistone.stone_guardian_tooltip"));
			button.setState(props.getAbility(Buttons.StoneGuardian.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.StoneFire)) {
			ToggleButton button = new ToggleButton(Buttons.StoneFire, firstColumn, fourthRow, Utils.localize("text.guistone.stone_fire_button"), Utils.localize("text.guistone.stone_fire_tooltip"));
			button.setState(props.getAbility(Buttons.StoneFire.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.TimeSpace)) {
			ToggleButton button = new ToggleButton(Buttons.TimeSpace, secondColumn, firstRow, Utils.localize("text.guistone.timespace_button"), Utils.localize("text.guistone.timespace_tooltip"));
			button.setState(props.getAbility(Buttons.TimeSpace.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.TimeSpaceEthereal)) {
			ToggleButton button = new ToggleButton(Buttons.TimeSpaceEthereal, secondColumn, secondRow, Utils.localize("text.guistone.timespace_ethereal_button"), Utils.localize("text.guistone.timespace_ethereal_tooltip"));
			button.setState(props.getAbility(Buttons.TimeSpaceEthereal.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.TimeSpaceGuardian)) {
			ToggleButton button = new ToggleButton(Buttons.TimeSpaceGuardian, secondColumn, thirdRow, Utils.localize("text.guistone.timespace_guardian_button"), Utils.localize("text.guistone.timespace_guardian_tooltip"));
			button.setState(props.getAbility(Buttons.TimeSpaceGuardian.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.TimeSpaceFire)) {
			ToggleButton button = new ToggleButton(Buttons.TimeSpaceFire, secondColumn, fourthRow, Utils.localize("text.guistone.timespace_fire_button"), Utils.localize("text.guistone.timespace_fire_tooltip"));
			button.setState(props.getAbility(Buttons.TimeSpaceFire.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.Pathfinder)) {
			ToggleButton button = new ToggleButton(Buttons.Pathfinder, thirdColumn, firstRow, Utils.localize("text.guistone.pathfinder_button"), Utils.localize("text.guistone.pathfinder_tooltip"));
			button.setState(props.getAbility(Buttons.Pathfinder.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.PathfinderEthereal)) {
			ToggleButton button = new ToggleButton(Buttons.PathfinderEthereal, thirdColumn, secondRow, Utils.localize("text.guistone.pathfinder_ethereal_button"), Utils.localize("text.guistone.pathfinder_ethereal_tooltip"));
			button.setState(props.getAbility(Buttons.PathfinderEthereal.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.PathfinderGuardian)) {
			ToggleButton button = new ToggleButton(Buttons.PathfinderGuardian, thirdColumn, thirdRow, Utils.localize("text.guistone.pathfinder_guardian_button"), Utils.localize("text.guistone.pathfinder_guardian_tooltip"));
			button.setState(props.getAbility(Buttons.PathfinderGuardian.getSlot()));
			this.buttonList.add(button);
		}
		if (inventory.hasGemInSlot(Slots.PathfinderFire)) {
			ToggleButton button = new ToggleButton(Buttons.PathfinderFire, thirdColumn, fourthRow, Utils.localize("text.guistone.pathfinder_fire_button"), Utils.localize("text.guistone.pathfinder_fire_tooltip"));
			button.setState(props.getAbility(Buttons.PathfinderFire.getSlot()));
			this.buttonList.add(button);
		}
		
		GuiButton invButton = new BasicButton(Buttons.Inv, left + xSize - 40, top + ySize - 30, 20, Utils.localize("text.guistone.inv"));
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
		Buttons btn = (Buttons)((BasicButton)button).getButtonType();
				
		switch (btn) {
		case Inv:
			NetworkOverlord.get(ModInfo.ID).sendToServer(new OpenGuiMessage(GuiEnum.STONE_INVENTORY));
			break;
		case NoFallDmg:
		case StoneEthereal:
		case StoneGuardian:
		case StoneFire:
		case Pathfinder:
		case PathfinderEthereal:
		case PathfinderGuardian:
		case PathfinderFire:
		case TimeSpace:
		case TimeSpaceEthereal:
		case TimeSpaceGuardian:
		case TimeSpaceFire:
			toggleButton(button);
			props.setAbility(btn.getSlot(), ((ToggleButton) button).isOn());
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
