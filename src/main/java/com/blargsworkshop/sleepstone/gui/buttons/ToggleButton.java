package com.blargsworkshop.sleepstone.gui.buttons;

import cpw.mods.fml.common.registry.LanguageRegistry;

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
	
	private void init() {
		setOff();
	}
	
	public void setOn() {
		setDisplayString(coreText + ": " + on);
		isOn = true;
	}
	
	public void setOff() {
		setDisplayString(coreText + ": " + off);
		isOn = false;
	}
	
	public void toggle() {
		if (isOn) {
			setOff();
		}
		else {
			setOn();
		}
	}
	
	public boolean isOn() {
		return isOn;
	}
	
	private String localize(String messageKey) {
		return LanguageRegistry.instance().getStringLocalization(messageKey);
	}

}
