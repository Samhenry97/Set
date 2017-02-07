package com.henry.set.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.henry.set.util.Const;

public class Log {
	
	private int time;
	private int endTime;
	private int textWidth;
	private String message;
	
	public  boolean good;
	
	public Log(String message, boolean good) {
		this.message = message;
		this.good = good;
		time = 0;
		endTime = Const.LOG_TIME;
		
		BufferedImage b = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.createGraphics();
		
		FontMetrics metrics = g.getFontMetrics(Const.LOG_FONT);
		textWidth = metrics.stringWidth(message);
	}
	
	public void update() {
		if(time++ > endTime) {
			Logger.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setFont(Const.LOG_FONT);
		
		if(good) {
			g.setColor(Color.green);
			g.drawString(message, Const.MARGIN_LOGGER, Panel.HEIGHT - Const.MARGIN_LOGGER);
		} else {
			g.setColor(Color.red);
			g.drawString(message, Panel.WIDTH - Const.MARGIN_LOGGER - textWidth, Panel.HEIGHT - Const.MARGIN_LOGGER);
		}
	}

}
