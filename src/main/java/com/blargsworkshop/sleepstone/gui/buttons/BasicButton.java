package com.blargsworkshop.sleepstone.gui.buttons;

public class BasicButton extends TooltipButton {

	private final static int defaultWidth = 120;
	private final static int defaultHeight = 20;
	
	protected final Enum<?> buttonType;
	
	/**
	 * Creates a button to be used on the Sleepstone Gui
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
	 * Creates a button to be used on the Sleepstone Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param width Width of the button. Default is 70.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(Enum<?> type, int left, int top, int width, String text) {
		this(type, left, top, width, defaultHeight, text);
	}
	
	/**
	 * Creates a button to be used on the Sleepstone Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param text Text that appears on the button.
	 */
	public BasicButton(Enum<?> type, int left, int top, String text) {
		this(type, left, top, defaultWidth, defaultHeight, text);
	}
	
	/**
	 * Creates a button to be used on the Sleepstone Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param width Width of the button. Default is 70.
	 * @param height Height of the button. Default is 20.
	 * @param text Text that appears on the button.
	 * @param tooltip Text that appears as the ToolTip.
	 */
	public BasicButton(Enum<?> type, int left, int top, int width, int height, String text, String tooltip) {
		super(type.ordinal(), left, top, width, height, text, tooltip);
		buttonType = type;
	}
	
	/**
	 * Creates a button to be used on the Sleepstone Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param width Width of the button. Default is 70.
	 * @param text Text that appears on the button.
	 * @param tooltip Text that appears as the ToolTip.
	 */
	public BasicButton(Enum<?> type, int left, int top, int width, String text, String tooltip) {
		this(type, left, top, width, defaultHeight, text, tooltip);
	}
	
	/**
	 * Creates a button to be used on the Sleepstone Gui
	 * @param type Enum that matches the button.  Used in actionPerformed(button).
	 * @param left Left edge of the button on the whole screen.
	 * @param top Top edge of the button on the whole screen.
	 * @param text Text that appears on the button.
	 * @param tooltip Text that appears as the ToolTip.
	 */
	public BasicButton(Enum<?> type, int left, int top, String text, String tooltip) {
		this(type, left, top, defaultWidth, defaultHeight, text, tooltip);
	}
	
	public Enum<?> getButtonType() {
		return buttonType;
	}
	
	public void setDisplayString(String text) {
		this.displayString = text;
	}

}
