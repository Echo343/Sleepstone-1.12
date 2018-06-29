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
import com.blargsworkshop.sleepstone.SleepstoneMod;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.gui.GuiEnum;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventory;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventoryProvider;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage;
import com.blargsworkshop.sleepstone.network.packets.toserver.CommandMessage.Command;
import com.blargsworkshop.sleepstone.network.packets.toserver.OpenGuiMessage;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


public class AbilityGui extends GuiScreen {
	
	protected static enum Button {
		VENOM_IMMUNITY(Ability.VENOM_IMMUNITY, ToggleButton.class, 0, 0),
		ETHEREAL_FEET(Ability.ETHEREAL_FEET, ToggleButton.class, 0, 1),
		ROCK_BARRIER(Ability.ROCK_BARRIER, BasicButton.class, 0, 2),
		PRECOGNITION(Ability.PRECOGNITION, ToggleButton.class, 1, 0),
		TEMPORAL_AID(Ability.TEMPORAL_AID, BasicButton.class, 1, 1),
		HELLJUMPER(Ability.HELLJUMPER, BasicButton.class, 1, 2),
		IRON_STOMACH(Ability.IRON_STOMACH, ToggleButton.class, 2, 0),
		PHANTOM_TORCH(Ability.PHANTOM_TORCH, BasicButton.class, 2, 1),
		WINDWALKER(Ability.WINDWALKER, ToggleButton.class, 2, 2),
		INV,
		WARP;
		
		private static final String MESSAGE_KEY_PREFIX = "text.guistone.";
		private Ability ability = null;
		private Class<? extends BasicButton> type = null;
		private int column = 0;
		private int row = 0;
		
		Button() {}
		
		Button(Ability ability, Class<? extends BasicButton> buttonType, int col, int row) {
			this.ability = ability;
			this.type = buttonType;
			this.column = col;
			this.row = row;
		}
		
		public boolean hasAbility() {
			return ability != null;
		}
		
		public Ability getAbility() {
			return ability;
		}
		
		public Class<? extends BasicButton> getType() {
			return type;
		}
		
		public int getColumn() {
			return column;
		}
		
		public int getRow() {
			return row;
		}
		
		public String getMessageKey() {
			return MESSAGE_KEY_PREFIX + this.name().toLowerCase() + "_button";
		}
		
		public String getTooltipMessageKey() {
			return MESSAGE_KEY_PREFIX + this.name().toLowerCase() + "_tooltip";
		}
	}
	
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/guistone.png");
	
	private static final int X_SIZE = 248;
//	private static final int xSize = 376;
	private static final int Y_SIZE = 166;
	private static final int LEFT_MARGIN = 4;
	private static final int TOP_MARGIN = 4;
	private static final int HORIZONTAL_SPACING = 4;
	private static final int VERTICAL_SPACING = -1;
	private static final int BUTTON_SCREEN_WIDTH = BasicButton.DEFAULT_WIDTH * 3 + LEFT_MARGIN * 2 + HORIZONTAL_SPACING * 2;
	private static final int EFFECTIVE_BUTTON_WIDTH = BasicButton.DEFAULT_WIDTH + HORIZONTAL_SPACING;
	private static final int EFFECTIVE_BUTTON_HEIGHT = BasicButton.DEFAULT_HEIGHT + VERTICAL_SPACING;
	private StoneInventory inventory;
	private EntityPlayer player;
	private IAbilityStatus props;
	
	private TooltipContainer buttonTooltips = new TooltipContainer();
	
	public AbilityGui(EntityPlayer player, ItemStack stone) {
		this.inventory = StoneInventoryProvider.getStoneInventory(stone);
		this.player = player;
		this.props = AbilityStatusProvider.getAbilityStatus(player);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		if (props == null) {
			Log.error("Error getting props", player);
			return;			
		}
		initButtons();
	}
	
	private boolean shouldShowButton(Button button) {
		return inventory.hasGemInSlot(button.getAbility());
	}
	
	protected void initButtons() {
		int left = (this.width - X_SIZE) / 2;
		int btnLeft = (this.width - BUTTON_SCREEN_WIDTH) / 2;
		int top = (this.height - Y_SIZE) / 2;
		
		int firstRow = top + TOP_MARGIN;
		int firstColumn = btnLeft + LEFT_MARGIN;
		
		BasicButton button = null;
		Tooltip tooltip = null;
		
		for (Button btn : Button.values()) {
			if (btn.hasAbility() && shouldShowButton(btn)) {
				int rowPosition = firstRow + btn.getRow() * EFFECTIVE_BUTTON_HEIGHT;
				int columnPosition = firstColumn + btn.getColumn() * EFFECTIVE_BUTTON_WIDTH;
				
				if (btn.getType().equals(ToggleButton.class)) {
					button = new ToggleButton(btn, columnPosition, rowPosition, Utils.localize(btn.getMessageKey()));
					((ToggleButton) button).setState(props.getAbility(btn.getAbility()));
				}
				else if (btn.getType().equals(BasicButton.class)) {
					button = new BasicButton(btn, columnPosition, rowPosition, Utils.localize(btn.getMessageKey()));
				}
				
				tooltip = new Tooltip(button, Utils.localize(btn.getTooltipMessageKey()));
				buttonTooltips.add(tooltip);
				buttonList.add(button);
			}
		}
		
		// Non-ability buttons are handled separately (ie. Inv, Warp)
		GuiButton invButton = new BasicButton(Button.INV, left + X_SIZE - 40, top + Y_SIZE - 30, 20, Utils.localize(Button.INV.getMessageKey()));
		this.buttonList.add(invButton); // TODO Use an icon of some sort.
		
		int warpButtonXPos = (left - BasicButton.DEFAULT_WIDTH) / 2;
		int warpButtonYPos = top + Y_SIZE - BasicButton.DEFAULT_HEIGHT - 10;
		GuiButton warpButton = new BasicButton(Button.WARP, warpButtonXPos, warpButtonYPos, Utils.localize(Button.WARP.getMessageKey()));
		this.buttonList.add(warpButton);
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
		
		//Draw Tooltips last so they are on top.
		buttonTooltips.draw(mc, mouseX, mouseY);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		Button btn = (Button)((BasicButton)button).getButtonType();
				
		switch (btn) {
			case WARP:
				NetworkOverlord.get(ModInfo.ID).sendToServer(new CommandMessage(Command.WARP));
				player.closeScreen();
				break;
			case INV:
				NetworkOverlord.get(ModInfo.ID).sendToServer(new OpenGuiMessage(GuiEnum.STONE_INVENTORY));
				break;
			case ROCK_BARRIER:
				NetworkOverlord.get(ModInfo.ID).sendToServer(new CommandMessage(Command.ROCKWALL));
				player.closeScreen();
				break;
			case ETHEREAL_FEET:
			case IRON_STOMACH:
			case PRECOGNITION:
			case VENOM_IMMUNITY:
				toggleButton(button);
				props.setAbility(btn.getAbility(), ((ToggleButton) button).isOn());
				break;
			case WINDWALKER:
				toggleButton(button);
				props.setAbility(btn.getAbility(), ((ToggleButton) button).isOn());
				player.closeScreen();
				break;
			case HELLJUMPER:
			case PHANTOM_TORCH:
				break;
			case TEMPORAL_AID:
				player.openGui(SleepstoneMod.getInstance(), GuiEnum.TEMPORAL_AID_PLAYER_SELECTION.ordinal(), player.getEntityWorld(), (int) player.posX, (int) player.posY, (int) player.posZ);
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
