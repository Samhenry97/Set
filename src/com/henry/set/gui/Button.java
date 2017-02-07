package com.henry.set.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.henry.set.input.Mouse;
import com.henry.set.util.Const;

public class Button implements Mouse.Clickable {
	
	public static interface ButtonAction {
		public void onPress();
		public void onRelease();
	}
	
	public Rectangle rect;
	public boolean clicked = false;
	public String text;
	public int textWidth, textHeight;
	public float xRatio, yRatio;
	public ButtonAction action;
	public boolean fromCorner;
	private boolean hover = false;
	
	public Button(String text, int x, int y) {
		this(text, x, y, false);
	}
	
	public Button(String text, int x, int y, boolean fromCorner) {
		this.text = text;
		this.fromCorner = fromCorner;
		rect = new Rectangle();
		updateSize();
		
		xRatio = (float) x / Const.WINDOW_WIDTH;
		yRatio = (float) y / Const.WINDOW_HEIGHT;
		
		updateLocation();
	}
	
	public void onPress() {
		if(hover) {
			clicked = true;
			action.onPress();
		}
	}
	
	public void onRelease() {
		if(hover && clicked) {
			clicked = false;
			action.onRelease();
		} else if(clicked) {
			clicked = false;
		}
	}
	
	public void updatePosition() {
		updateLocation();
	}
	
	public void updateLocation() {
		if(fromCorner) {
			rect.setLocation((int) (Panel.WIDTH * xRatio), (int) (Panel.HEIGHT * yRatio));
		} else {
			rect.setLocation((int) (Panel.WIDTH * xRatio - rect.width / 2), (int) (Panel.HEIGHT * yRatio - rect.height / 2));
		}
	}
	
	private void updateSize() {
		BufferedImage b = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.createGraphics();
		
		FontMetrics metrics = g.getFontMetrics(Const.BUTTON_FONT);
		textWidth = metrics.stringWidth(text);
		textHeight = metrics.getAscent();
		rect.width = textWidth + Const.BUTTON_PADDING * 2;
		rect.height = textHeight + Const.BUTTON_PADDING * 2;
	}
	
	public void update() {
		hover = rect.contains(Mouse.x, Mouse.y);
	}
	
	public void render(Graphics g) {
		g.setFont(Const.BUTTON_FONT);
		
		if(hover && !clicked) {
			g.setColor(Const.BUTTON_HOVER);
			g.fill3DRect(rect.x, rect.y, rect.width, rect.height, true);
		} else if(hover && clicked) {
			g.setColor(Const.BUTTON_COLOR);
			g.fill3DRect(rect.x, rect.y, rect.width, rect.height, false);
		} else {
			g.setColor(Const.BUTTON_COLOR);
			g.fill3DRect(rect.x, rect.y, rect.width, rect.height, true);
		}
		
		g.setColor(Color.white);
		g.drawString(text, Const.BUTTON_PADDING + rect.x, Const.BUTTON_PADDING + rect.y + textHeight);
	}

}
