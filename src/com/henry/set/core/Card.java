package com.henry.set.core;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.henry.set.input.Mouse;
import com.henry.set.util.Images;

public class Card {
	public static class Shape { 
		public final String str;
		public Shape(String name) { this.str = name; }
	}
	
	public static class Color {	
		public final String str;
		public Color(String name) { this.str = name; }
	}
	
	public static class Number {
		public final String str;
		public Number(String name) { this.str = name; }
	}
	
	public static class Shade {
		public final String str;
		public Shade(String name) { this.str = name; }
	}
	
	public static Shape[] shapes = { new Shape("wiggle"), new Shape("oval"), new Shape("diamond") };
	public static Color[] colors = { new Color("purple"), new Color("green"), new Color("red") };
	public static Number[] numbers = { new Number("one"), new Number("two"), new Number("three") };
	public static Shade[] shades = { new Shade("solid"), new Shade("lines"), new Shade("clear") };
	
	//******************************************************************************************//
	//******************************************************************************************//
	//******************************************************************************************//
	
	public Shape shape;
	public Color color;
	public Number number;
	public Shade shade;
	public String name;
	
	public Rectangle rect;
	
	public boolean hover = false;
	public boolean clicking = false;
	public boolean selected = false;
	public boolean highlighted = false;
	
	private float animAlpha = 100;
	private int minAnimAlpha = 50;
	private int maxAnimAlpha = 150;
	private float animAlphaSpeed = 0.1f;
	private int animAlphaDir = 1;
	
	public Card(Shape shape, Color color, Number number, Shade shade) {
		this.shape = shape;
		this.color = color;
		this.number = number;
		this.shade = shade;
		name = shape.str + color.str + number.str + shade.str;
		rect = new Rectangle(0, 0, 0, 0);
		
		Images.addImage(name);
	}
	
	public void update() {
		hover = rect.contains(Mouse.x, Mouse.y);
		clicking = hover && Mouse.isButtonDown(1);
		
		if(highlighted) {
			animAlpha += animAlphaDir * animAlphaSpeed;
			if(animAlphaDir == 1 && animAlpha >= maxAnimAlpha) {
				animAlphaDir = -animAlphaDir;
			} else if(animAlphaDir == -1 && animAlpha <= minAnimAlpha) {
				animAlphaDir = -animAlphaDir;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Images.get(name), rect.x, rect.y, rect.width, rect.height, null);
		
		if(hover) {
			g.setColor(new java.awt.Color(100, 100, 100, 100));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		if(clicking) {
			g.setColor(new java.awt.Color(100, 100, 100, 200));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		if(selected) {
			g.setColor(new java.awt.Color(0, 50, 100, 100));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		if(highlighted) {
			g.setColor(new java.awt.Color(100, 255, 0, (int) animAlpha));
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Card)) { return false; }
		Card card = (Card) other;
		return name == card.name;
	}
	
	public void highlight() {
		animAlpha = 100;
		animAlphaDir = 1;
		highlighted = true;
	}
	
}
