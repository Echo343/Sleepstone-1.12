package com.blargsworkshop.sleepstone.gui.buttons;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public abstract class TooltipButton extends GuiButton {
	private static final String TOOLTIP_CHAR = "?";
	private final static int LINE_HEIGHT = 11;
	private long mouseoverTime = 0;
	private long prevSystemTime = -1;
	private String toolTip;
	
	/** The amount of time in milliseconds until a tooltip is rendered */
	private long tooltipDelay = 900;
	
	/** The maximum width in pixels a tooltip can occupy before word wrapping occurs */
	private int tooltipMaxWidth = 150;
	
	/** Putting this string into a tooltip will cause a line break */
    private String tooltipNewlineDelimeter = "_p"; //"�p";	//the "�" symbol doesn't seem to work
	
	private int tooltipXOffset = 0;
	private int tooltipYOffset = 10;
	private int indicatorColor = 0xFFFFFF; // 0x99FFFFFF
	private int indicatorMouseOverColor = 0xFFFFFF;
	
	

	public TooltipButton(int id, int left, int top, int width, int height, String text) {
		super(id, left, top, width, height, text);
	}
	
	public TooltipButton(int id, int left, int top, String text)
    {
        super(id, left, top, 200, 20, text);
    }
	
	/**
	 * Sets a tooltip.  If a tooltip is present the button will automatically
	 * show an indicator that a tooltip is present.
	 * @param text localized text for the tooltip.
	 */
	public void setToolTip(String text) {
		toolTip = text;
	}
	
	public String getToolTip() {
		return toolTip;
	}
	
	public boolean hasToolTip() {
		if (toolTip != null && !toolTip.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Only use this in draw method after the super has been called.
	 * @return true if mousedOver, false if disable or not mousedOver.
	 */
	protected boolean isButtonMouseOver() {
		if (this.getHoverState(this.field_146123_n) == 2) {
			return true;
		}
		return false;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);
		if (hasToolTip()) {
			if (isButtonMouseOver()) {
				RenderTooltipButtonMouseoverEffect(mc.fontRenderer);
				long systemTime = System.currentTimeMillis();
				
				if (prevSystemTime > 0) {
					mouseoverTime += systemTime - prevSystemTime;
				}
				prevSystemTime = systemTime;
			}
			else {
				RenderTooltipButtonEffect(mc.fontRenderer);
				mouseoverTime = 0;
				prevSystemTime = -1;
			}
			
			if (mouseoverTime > getTooltipDelay()) {
				DrawTooltip(mc.fontRenderer, mouseX, mouseY);
			}
		}
	}
	
	/**
	 * Renders a tooltip at (x,y).
	 * @param x
	 * @param y
	 * @param tooltip
	 */
	protected void DrawTooltip(FontRenderer fontRenderer, int x, int y)
	{
		String[] tooltipArray = ParseTooltipArrayFromString(fontRenderer);

        int tooltipWidth = GetTooltipWidth(fontRenderer, tooltipArray);
        int tooltipHeight = GetTooltipHeight(fontRenderer, tooltipArray);
		
        int tooltipX = x + getTooltipXOffset();
        int tooltipY = y + getTooltipYOffset();

        if(tooltipX > width - tooltipWidth - 7) {
        	tooltipX = width - tooltipWidth - 7;
        }
        if(tooltipY > height -  tooltipHeight - 8) {
        	tooltipY = height -  tooltipHeight - 8;
        }
        
        //render the background inside box
        int innerAlpha = -0xFEFFFF0;	//very very dark purple
        drawGradientRect(tooltipX, tooltipY - 1, tooltipX + tooltipWidth + 6, tooltipY, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 6, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 7, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 6, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX - 1, tooltipY, tooltipX, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        drawGradientRect(tooltipX + tooltipWidth + 6, tooltipY, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, innerAlpha, innerAlpha);
        
        //render the background outside box
        int outerAlpha1 = 0x505000FF;
        int outerAlpha2 = (outerAlpha1 & 0xFEFEFE) >> 1 | outerAlpha1 & -0x1000000;
        drawGradientRect(tooltipX, tooltipY + 1, tooltipX + 1, tooltipY + tooltipHeight + 6 - 1, outerAlpha1, outerAlpha2);
        drawGradientRect(tooltipX + tooltipWidth + 5, tooltipY + 1, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6 - 1, outerAlpha1, outerAlpha2);
        drawGradientRect(tooltipX, tooltipY, tooltipX + tooltipWidth + 3, tooltipY + 1, outerAlpha1, outerAlpha1);
        drawGradientRect(tooltipX, tooltipY + tooltipHeight + 5, tooltipX + tooltipWidth + 7, tooltipY + tooltipHeight + 6, outerAlpha2, outerAlpha2);
        
        //render the foreground text
        int lineCount = 0;
        for (String s : tooltipArray)
        {
            fontRenderer.drawString(s, tooltipX + 2, tooltipY + 2 + lineCount * LINE_HEIGHT, 0xFFFFFF);
            lineCount++;
        }
	}
	
	/**
	 * Draws the tooltip indicator.
	 * @param mc
	 */
	protected void RenderTooltipButtonEffect(FontRenderer fontRenderer)
	{
		boolean flag = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(true);
		fontRenderer.drawString(EnumChatFormatting.AQUA + getIndicatorString(), getIndicatorXPos(), getIndicatorYPos(), getIndicatorColor());
		fontRenderer.setUnicodeFlag(flag);
	}
	
	protected void RenderTooltipButtonMouseoverEffect(FontRenderer fontRenderer)
	{
		boolean flag = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(true);
		fontRenderer.drawString(EnumChatFormatting.AQUA + getIndicatorString(), getIndicatorXPos(), getIndicatorYPos(), getIndicatorMouseOverColor());
		fontRenderer.setUnicodeFlag(flag);
	}
	
	/**
	 * Converts a String representation of a tooltip into a String[], and also decodes any font codes used.
	 * @param s Ex: "Hello,_nI am your _ltooltip_r and you love me."
	 * @return An array of Strings such that each String width does not exceed tooltipMaxWidth
	 */
	private String[] ParseTooltipArrayFromString(FontRenderer fontRenderer)
	{
		String[] tooltipSections = getToolTip().split(tooltipNewlineDelimeter);
		ArrayList<String> tooltipArrayList = new ArrayList<String>();
		
		for(String section : tooltipSections)
		{
			String tooltip = "";
			String[] tooltipWords = section.split(" ");
			
			for(int i = 0; i < tooltipWords.length; i++)
			{
				int lineWidthWithNextWord = fontRenderer.getStringWidth(tooltip + tooltipWords[i]);
				if(lineWidthWithNextWord > tooltipMaxWidth)
				{
					tooltipArrayList.add(tooltip.trim());
					tooltip = tooltipWords[i] + " ";
				}
				else
				{
					tooltip += tooltipWords[i] + " ";
				}
			}
			
			tooltipArrayList.add(tooltip.trim());
		}

		String[] tooltipArray = new String[tooltipArrayList.size()];
		tooltipArrayList.toArray(tooltipArray);
		
		return tooltipArray;
	}
	
	/***
	 * Gets the width of the tooltip in pixels.
	 * @param tooltipArray
	 * @return
	 */
	private int GetTooltipWidth(FontRenderer fontRenderer, String[] tooltipArray)
	{
		int longestWidth = 0;
		for(String s : tooltipArray)
		{
			int width = fontRenderer.getStringWidth(s);
			if(width > longestWidth)
				longestWidth = width;
		}
		return longestWidth;
	}
	
	/**
	 * Gets the height of the tooltip in pixels.
	 * @param tooltipArray
	 * @return
	 */
	private int GetTooltipHeight(FontRenderer fontRenderer, String[] tooltipArray)
	{
		int tooltipHeight = fontRenderer.FONT_HEIGHT - 2;
        if (tooltipArray.length > 1)
        {
        	tooltipHeight += (tooltipArray.length - 1) * LINE_HEIGHT;
        }
        return tooltipHeight;
	}
	
	protected String getIndicatorString() {
		return TOOLTIP_CHAR;
	}

	protected int getIndicatorYPos() {
		return this.yPosition;
	}
	
	protected int getIndicatorXPos() {
		return this.xPosition + this.getButtonWidth() - 5;
	}

	public int getIndicatorColor() {
		return indicatorColor;
	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
	}

	public int getIndicatorMouseOverColor() {
		return indicatorMouseOverColor;
	}

	public void setIndicatorMouseOverColor(int indicatorMouseOverColor) {
		this.indicatorMouseOverColor = indicatorMouseOverColor;
	}

	protected long getTooltipDelay() {
		return tooltipDelay;
	}

	protected void setTooltipDelay(long tooltipDelay) {
		this.tooltipDelay = tooltipDelay;
	}

	protected int getTooltipMaxWidth() {
		return tooltipMaxWidth;
	}

	protected void setTooltipMaxWidth(int tooltipMaxWidth) {
		this.tooltipMaxWidth = tooltipMaxWidth;
	}

	protected int getTooltipXOffset() {
		return tooltipXOffset;
	}

	protected void setTooltipXOffset(int tooltipXOffset) {
		this.tooltipXOffset = tooltipXOffset;
	}

	protected int getTooltipYOffset() {
		return tooltipYOffset;
	}

	protected void setTooltipYOffset(int tooltipYOffset) {
		this.tooltipYOffset = tooltipYOffset;
	}
}