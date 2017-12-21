package com.blargsworkshop.engine.gui.buttons;

import com.blargsworkshop.engine.gui.IDrawable;

import net.minecraft.client.Minecraft;

interface ITooltip extends IDrawable {

	public Tooltip drawIndicatorsAndDetect(Minecraft mc, int mouseX, int mouseY);
	
}
