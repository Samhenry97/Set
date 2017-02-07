package com.henry.set.core;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.henry.set.input.Mouse;
import com.henry.set.util.Const;

public class Player {
	
	public ArrayList<Set> sets;
	public Rectangle rect;
	public String name;
	public boolean selected = false;
	public boolean hover = false;
	public int textWidth, textHeight;
	
	private Game game;
	
	public Player(Game game, String name) {
		this.name = name;
		this.game = game;
		 
		sets = new ArrayList<Set>();
		rect = new Rectangle();
		
		calcTextOffset();
	}
	
	public void update() {
		hover = rect.contains(Mouse.x, Mouse.y);
		
		if(hover && Mouse.isButtonDown(1)) {
			selected = true;
			game.selectPlayer(this);
		}
	}
	
	public void calcTextOffset() {
		BufferedImage b = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.createGraphics();
		
		FontMetrics metrics = g.getFontMetrics(Const.PLAYER_FONT);
		textWidth = rect.x + rect.width / 2 - metrics.stringWidth(name) / 2;
		textHeight = metrics.getAscent();
	}
	
	public void render(Graphics g) {
		g.setFont(Const.PLAYER_FONT);
		
		g.setColor(Const.PLAYER_COLOR);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
		g.setColor(Color.gray);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		if(selected) {
			g.setColor(new Color(100, 0, 200, 200));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		if(hover) {
			g.setColor(new Color(200, 200, 0, 100));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		g.setColor(Color.white);
		g.drawString(name + " - " + Integer.toString(sets.size()), textWidth, rect.y + Const.PLAYER_PADDING + textHeight);
	}
	
	public void addSet(Set s) {
		sets.add(s);
	}
	
	public void deselect() {
		selected = false;
	}

}
