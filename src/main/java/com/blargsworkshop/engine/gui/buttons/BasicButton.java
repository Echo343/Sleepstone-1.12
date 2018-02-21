package com.blargsworkshop.engine.gui.buttons;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BasicButton extends GuiButton {
	
	public enum HoverState {
		DISABLED,
		HOVER,
		NOT
	};

	public final static int DEFAULT_WIDTH = 120;
	public final static int DEFAULT_HEIGHT = 20;
	
	protected final Enum<?> buttonType;
	
	/**
	 * Creates a button to be used on a Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param width Width of the button. Default is 70.
	 * @param height Height of the button. Default is 20.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(Enum<?> type, int left, int top, int width, int height, String text) {
		super(type.ordinal(), left, top, width, height, text);
		buttonType = type;
	}
	
	/**
	 * Creates a button to be used on a Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param width Width of the button. Default is 70.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(Enum<?> type, int left, int top, int width, String text) {
		this(type, left, top, width, DEFAULT_HEIGHT, text);
	}
	
	/**
	 * Creates a button to be used on a Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(Enum<?> type, int left, int top, String text) {
		this(type, left, top, DEFAULT_WIDTH, DEFAULT_HEIGHT, text);
	}
	
	/**
	 * Creates a button to be used on a Gui
	 * @param id int that defines the id of the button.  Referenced in actionPerformed(button)
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(int id, int left, int top, String text) {
		super(id, left, top, DEFAULT_WIDTH, DEFAULT_HEIGHT, text);
		buttonType = null;
	}
	
	public Enum<?> getButtonType() {
		return buttonType;
	}
	
	public void setDisplayString(String text) {
		this.displayString = text;
	}
	
	public HoverState getHoverState() {
		int state = this.getHoverState(this.isMouseOver());
		switch (state) {
			case 0: return HoverState.DISABLED;
			case 1: return HoverState.NOT;
			case 2: return HoverState.HOVER;
			default: return HoverState.NOT;
		}
	}

}
