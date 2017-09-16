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
	
	private void init() {
		setOff();
	}
	
	public void setOn() {
		setDisplayString(coreText + ": " + EnumChatFormatting.GREEN + on + EnumChatFormatting.WHITE);
		isOn = true;
	}
	
	public void setOff() {
		setDisplayString(coreText + ": " + EnumChatFormatting.RED + off + EnumChatFormatting.WHITE);
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
	
	protected String getText() {
		return this.coreText;
	}
	
	private String localize(String messageKey) {
		return LanguageRegistry.instance().getStringLocalization(messageKey);
	}

}
