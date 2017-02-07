package com.henry.set.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.henry.set.core.Game;

public class Keyboard implements KeyListener {
	
	public static interface Typeable {
		public void onKeyPress(int code, char letter);
		public void onBackspace();
	}
	
	private static boolean keys[] = new boolean[5000];
	private static ArrayList<Typeable> typeables = new ArrayList<Typeable>();
	
	public static boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}
	
	public static void clearKeys() {
		for(int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}
	
	public static void subscribe(Typeable t) {
		typeables.add(t);
	}
	
	public static void unsubscribe(Typeable t) {
		typeables.remove(t);
	}
	
	//**********************************************************
	//**********************************************************
	
	private Game game;
	
	public Keyboard(Game game) {
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		if(typeables.size() != 0) {
			for(int i = 0; i < typeables.size(); i++) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					typeables.get(i).onBackspace();
				} else {
					typeables.get(i).onKeyPress(e.getKeyCode(), e.getKeyChar());
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
