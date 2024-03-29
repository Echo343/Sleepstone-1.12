package com.blargsworkshop.engine.gui.buttons;

import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ToggleButton extends BasicButton {
	
	protected final String on = Utils.localize("text.toggle_button.on");
	protected final String off = Utils.localize("text.toggle_button.off");
	
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
		turnOff();
	}
	
	/**
	 * Turns the state of the button to On.
	 */
	public void turnOn() {
		setDisplayString(coreText + ": " + TextFormatting.GREEN + on + TextFormatting.WHITE);
		isOn = true;
	}
	
	/**
	 * Turns the state of the button to Off.
	 */
	public void turnOff() {
		setDisplayString(coreText + ": " + TextFormatting.RED + off + TextFormatting.WHITE);
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
	 * @param state True = on.  False = off.
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
}
