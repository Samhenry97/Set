package com.henry.set.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.henry.set.input.Keyboard;
import com.henry.set.input.Mouse;
import com.henry.set.util.Const;

public class TextBox implements Keyboard.Typeable {
	
	public static interface TextBoxAction {
		void onEnter();
	}
	
	public Rectangle rect;
	public String text, defaultText;
	public boolean hover = false;
	public boolean active = false;
	public boolean fromCorner;
	public float xRatio, yRatio;
	public TextBoxAction action;
	
	private int textWidth, textHeight, maxChars;
	private int curTextWidth = 0;
	
	// Cursor stuffs
	private int cursorTime = 0;
	private int cursorMaxTime = 500;
	private boolean showCursor = true;
	
	public TextBox(String text, int x, int y) {
		this(text, "", x, y);
	}
	
	public TextBox(String text, String defaultText, int x, int y) {
		this(text, "", Const.TEXTBOX_MAXCHARS, x, y, false);
	}
	
	public TextBox(String text, int x, int y, boolean fromCorner) {
		this(text, "", Const.TEXTBOX_MAXCHARS, x, y, fromCorner);
	}
	
	public TextBox(String text, String defaultText, int maxChars, int x, int y, boolean fromCorner) {
		this.text = text;
		this.defaultText = defaultText;
		this.rect = new Rectangle();
		this.maxChars = maxChars;
		this.fromCorner = fromCorner;
		updateSize();
		
		xRatio = (float) x / Panel.WIDTH;
		yRatio = (float) y / Panel.HEIGHT;
		updateLocation();
	}
	
	public void onKeyPress(int code, char letter) {
		if((int) letter == 65535 || (int) letter == 9) { return; }
		
		if(curTextWidth < textWidth) {
			text += letter;
		}
		
		cursorTime = 0;
		showCursor = true;
	}
	
	public void onBackspace() {
		if(!text.equals("")) {
			if(Keyboard.isKeyDown(KeyEvent.VK_CONTROL)) {
				int i = text.lastIndexOf(' ');
				if(i == -1) { text = ""; }
				else { text = text.substring(0, i); }
			} else {
				text = text.substring(0, text.length() - 1);
			}
		}
		
		cursorTime = 0;
		showCursor = true;
	}
	
	public void update() {
		hover = rect.contains(Mouse.x, Mouse.y);
		
		if(active) {
			if(!hover && Mouse.isButtonDown(1)) {
				active = false;
				Keyboard.unsubscribe(this);
			}
			
			cursorTime++;
			if(cursorTime > cursorMaxTime) {
				cursorTime = 0;
				showCursor = !showCursor;
			}
		} else {
			if(hover && Mouse.isButtonDown(1)) {
				active = true;
				Keyboard.subscribe(this);
			}
		}
		
		if(active && Keyboard.isKeyDown(KeyEvent.VK_ENTER)) {
			action.onEnter();
		}
	}
	
	public void render(Graphics g) {
		g.setFont(Const.TEXTBOX_FONT);
		
		if(hover) {
			g.setColor(Const.TEXTBOX_HOVER);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		} else {
			g.setColor(Const.TEXTBOX_COLOR);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		g.setColor(Const.TEXTBOX_BORDER_COLOR);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		if(text.length() != 0) {
			if(active && showCursor) {
				FontMetrics metrics = g.getFontMetrics(Const.TEXTBOX_FONT);
				curTextWidth = metrics.stringWidth(text);
				g.setColor(Color.WHITE);
				g.fillRect(Const.TEXTBOX_PADDING + rect.x + curTextWidth, Const.TEXTBOX_PADDING + rect.y, 1, textHeight);
			}
			
			g.setColor(Color.WHITE);
			g.drawString(text, Const.TEXTBOX_PADDING + rect.x, Const.TEXTBOX_PADDING + rect.y + textHeight);
		} else {
			if(active && showCursor) {
				g.setColor(Color.WHITE);
				g.fillRect(Const.TEXTBOX_PADDING + rect.x, Const.TEXTBOX_PADDING + rect.y, 1, textHeight);
			}
			
			g.setColor(Color.GRAY);
			g.drawString(defaultText, Const.TEXTBOX_PADDING + rect.x, Const.TEXTBOX_PADDING + rect.y + textHeight);
		}
		
		if(active) {
			g.setColor(Const.TEXTBOX_ACTIVE);
			g.fillRect(Const.TEXTBOX_PADDING / 2 + rect.x, Const.TEXTBOX_PADDING / 2 + rect.y, textWidth + Const.TEXTBOX_PADDING, textHeight + Const.TEXTBOX_PADDING);
		}
	}
	
	public void updatePosition() {
		updateSize();
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
		
		String test = "";
		for(int i = 0; i < maxChars; i++) {
			test += "W";
		}
		
		FontMetrics metrics = g.getFontMetrics(Const.TEXTBOX_FONT);
		textWidth = metrics.stringWidth(test);
		textHeight = metrics.getAscent();
		rect.width = textWidth + Const.TEXTBOX_PADDING * 2;
		rect.height = textHeight + Const.TEXTBOX_PADDING * 2;
	}
}
