package com.henry.set.gui.menu;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.henry.set.core.Game;
import com.henry.set.gui.Button;
import com.henry.set.gui.TextBox;
import com.henry.set.input.Keyboard;
import com.henry.set.input.Mouse;

public class Menu implements Keyboard.Typeable {
	
	protected Game game;
	protected ArrayList<Button> buttons;
	protected ArrayList<TextBox> textBoxes;
	
	public Menu(Game game) {
		this.game = game;
		buttons = new ArrayList<Button>();
		textBoxes = new ArrayList<TextBox>();
	}
	
	public  void update() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update();
		}
		
		for(int i = 0; i < textBoxes.size(); i++) {
			textBoxes.get(i).update();
		}
	}
	
	public void onKeyPress(int code, char letter) {
		if(letter == '\t') {
			boolean found = false;
			
			for(int i = 0; i < textBoxes.size(); i++) {
				if(textBoxes.get(i).active) {
					found = true;
					textBoxes.get(i).active = false;
					Keyboard.unsubscribe(textBoxes.get(i));
					if(Keyboard.isKeyDown(KeyEvent.VK_SHIFT)) {
						TextBox next = textBoxes.get((i + textBoxes.size() - 1) % textBoxes.size());
						next.active = true;
						Keyboard.subscribe(next);
					} else {
						TextBox next = textBoxes.get((i + 1) % textBoxes.size());
						next.active = true;
						Keyboard.subscribe(next);
					}
					break;
				}
			}
			
			if(!found && textBoxes.size() != 0) {
				textBoxes.get(0).active = true;
				Keyboard.subscribe(textBoxes.get(0));
			}
		}
	}
	
	public void onBackspace() { ; }
	
	public void render(Graphics g) {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(g);
		}
		
		for(int i = 0; i < textBoxes.size(); i++) {
			textBoxes.get(i).render(g);
		}
	}
	
	public void updatePositions() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).updatePosition();
		}
		
		for(int i = 0; i < textBoxes.size(); i++) {
			textBoxes.get(i).updatePosition();
		}
	}
	
	public void activate() {
		for(int i = 0; i < buttons.size(); i++) {
			Mouse.subscribe(buttons.get(i));
		}
		
		Keyboard.subscribe(this);
	}
	
	public void deactivate() {
		for(int i = 0; i < buttons.size(); i++) {
			Mouse.unsubscribe(buttons.get(i));
		}
		
		for(TextBox t : textBoxes) {
			Keyboard.unsubscribe(t);
		}
		
		Keyboard.unsubscribe(this);
	}

}
