package com.blargsworkshop.sleepstone.gui.buttons;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.util.EnumChatFormatting;

public class ToggleButton extends BasicButton {
	
	protected final String on = localize("text.toggle_button.on");
	protected final String off = localize("text.toggle_button.off");
	
	private final String coreText;
	private boolean isOn;

	public ToggleButton(Enum<?> type, int left, int top, int width, int height, String text) {
		super(type, left, top, width, height, text);
		this.coreText = text;
		init();
	}

	public ToggleButton(Enum<?> type, int left, int top, int width, String text) {
		super(type, left, top, width, text);
		this.coreText = text;
		init();
	}

	public ToggleButton(Enum<?> type, int left, int top, String text) {
		super(type, left, top, text);
		this.coreText = text;
		init();
	}
	
	public ToggleButton(Enum<?> type, int left, int top, int width, int height, String text, String tooltip) {
		super(type, left, top, width, height, text, tooltip);
		this.coreText = text;
		init();
	}

	public ToggleButton(Enum<?> type, int left, int top, int width, String text, String tooltip) {
		super(type, left, top, width, text, tooltip);
		this.coreText = text;
		init();
	}

	public ToggleButton(Enum<?> type, int left, int top, String text, String tooltip) {
		super(type, left, top, text, tooltip);
		this.coreText = text;
		init();
	}
	
	private void init() {
		turnOff();
	}
	
	/**
	 * Turns the state of the button to On.
	 */
	public void turnOn() {
		setDisplayString(coreText + ": " + EnumChatFormatting.GREEN + on + EnumChatFormatting.WHITE);
		isOn = true;
	}
	
	/**
	 * Turns the state of the button to Off.
	 */
	public void turnOff() {
		setDisplayString(coreText + ": " + EnumChatFormatting.RED + off + EnumChatFormatting.WHITE);
		isOn = false;
	}
	
	/**
	 * Toggles the Button
	 */
	public void toggle() {
		if (isOn) {
			turnOff();
		}
		else {
			turnOn();
		}
	}
	
	/**
	 * Sets the state of the button on or off.
	 * @param isOn True = on.  False = off.
	 */
	public void setState(boolean state) {
		if (state) {
			turnOn();
		}
		else {
			turnOff();
		}
	}
	
	public boolean isOn() {
		return isOn;
	}
	
	protected String getText() {
		return this.coreText;
	}
	
	private String localize(String messageKey) {
		return LanguageRegistry.instance().getStringLocalization(messageKey);
	}

}
