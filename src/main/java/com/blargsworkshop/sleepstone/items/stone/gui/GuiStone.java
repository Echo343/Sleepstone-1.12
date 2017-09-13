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
		NoFall,
		Inv
	}
	
	private static final ResourceLocation backgroundImage = new ResourceLocation(ModInfo.ID, "textures/gui/GuiStone.png");
	
	private final int xSize = 248;
	private final int ySize = 166;
	
	private final String onStr = localize("text.guistone.on");
	private final String offStr = localize("text.guistone.off");
	private final String noFallStr = localize("text.guistone.no_fall_damage") + ": ";
	private boolean noFallToggle = false;
		
	public GuiStone() {
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
		
//		String onStr = localize("text.guistone.on");
//		String offStr = localize("text.guistone.off");
//		String noFallStr = localize("text.guistone.no_fall_damage") + ": ";
		
		GuiButton noFallButton = new StoneGuiButton(Buttons.NoFall, left + 4, top + 4, noFallStr + offStr);
		this.buttonList.add(noFallButton);
		
		GuiButton warpButton = new StoneGuiButton(Buttons.Warp, (this.width - 70) / 2, (this.height - 20) / 2, 70, 20, localize("text.guistone.warp"));
		GuiButton invButton = new StoneGuiButton(Buttons.Inv, left + xSize - 24, top + 4, 20, 20, localize("text.guistone.inv"));
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
		case NoFall:
			if (noFallToggle) {
				button.displayString = noFallStr + offStr;
			}
			else {
				button.displayString = noFallStr + onStr;
			}
			noFallToggle = !noFallToggle;
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
	
	//TODO move out into separate class, rename to be more generic.
	protected class StoneGuiButton extends GuiButton {

		protected final Buttons buttonType;
		
		/**
		 * Creates a button to be used on the Sleepstone Gui
		 * @param type Enum that matches the button.  Used in actionPerformed(button).
		 * @param left Left edge of the button on the whole screen.
		 * @param top Top edge of the button on the whole screen.
		 * @param width Width of the button. Default is 70.
		 * @param height Height of the button. Default is 20.
		 * @param text Text that appears on the button.
		 */
		public StoneGuiButton(Buttons type, int left, int top, int width, int height, String text) {
			super(type.ordinal(), left, top, width, height, text);
			buttonType = type;
		}
		
		/**
		 * Creates a button to be used on the Sleepstone Gui
		 * @param type Enum that matches the button.  Used in actionPerformed(button).
		 * @param left Left edge of the button on the whole screen.
		 * @param top Top edge of the button on the whole screen.
		 * @param width Width of the button. Default is 70.
		 * @param text Text that appears on the button.
		 */
		public StoneGuiButton(Buttons type, int left, int top, int width, String text) {
			this(type, left, top, width, 20, text);
		}
		
		/**
		 * Creates a button to be used on the Sleepstone Gui
		 * @param type Enum that matches the button.  Used in actionPerformed(button).
		 * @param left Left edge of the button on the whole screen.
		 * @param top Top edge of the button on the whole screen.
		 * @param text Text that appears on the button.
		 */
		public StoneGuiButton(Buttons type, int left, int top, String text) {
			this(type, left, top, 70, 20, text);
		}
		
		public Buttons getButtonType() {
			return buttonType;
		}
	}
}
