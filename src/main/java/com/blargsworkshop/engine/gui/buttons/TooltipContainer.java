package com.blargsworkshop.engine.gui.buttons;

import java.util.ArrayList;
import java.util.List;

import com.blargsworkshop.engine.gui.IDrawable;

import net.minecraft.client.Minecraft;

public class TooltipContainer implements IDrawable {
	private List<ITooltip> tooltips = new ArrayList<>();
	private List<IDrawable> tooltipsToDraw = new ArrayList<>();

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY) {
		tooltipsToDraw.clear();
		for (ITooltip tp : tooltips) {
			IDrawable drawTp = tp.drawIndicatorsAndDetect(mc, mouseX, mouseY);
			if (drawTp != null) {
				tooltipsToDraw.add(drawTp);
			}
		}
		for (IDrawable tp : tooltipsToDraw) {
			tp.draw(mc, mouseX, mouseY);
		}
	}
	
	public void add(ITooltip tooltip) {
		if (tooltip != null) {
			tooltips.add(tooltip);
		}
	}

}
