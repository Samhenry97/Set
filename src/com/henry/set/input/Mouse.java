package com.henry.set.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import com.henry.set.core.Game;

public class Mouse implements MouseListener, MouseMotionListener {
	
	public static interface Clickable {
		public void onPress();
		public void onRelease();
	}
	
	public static int x;
	public static int y;
	
	public static boolean buttons[] = new boolean[MouseInfo.getNumberOfButtons()];
	private static ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	
	private Game game;
	
	public static boolean isButtonDown(int buttonId) {
		return buttons[buttonId];
	}
	
	public static void clearButtons() {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = false;
		}
	}
	
	public static void subscribe(Clickable c) {
		clickables.add(c);
	}
	
	public static void unsubscribe(Clickable c) {
		clickables.remove(c);
	}
	
	public Mouse(Game game) {
		x = 0;
		y = 0;
		
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		buttons[e.getButton()] = true;
		
		for(int i = 0; i < clickables.size(); i++) {
			clickables.get(i).onPress();
		}
		
		game.clicked();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		buttons[e.getButton()] = false;
		
		for(int i = 0; i < clickables.size(); i++) {
			clickables.get(i).onRelease();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
