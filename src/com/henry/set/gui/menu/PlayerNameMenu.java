package com.henry.set.gui.menu;

import java.util.ArrayList;

import com.henry.set.core.Game;
import com.henry.set.core.Game.GameMode;
import com.henry.set.gui.Button;
import com.henry.set.gui.Button.ButtonAction;
import com.henry.set.gui.Logger;
import com.henry.set.gui.Panel;
import com.henry.set.gui.TextBox;
import com.henry.set.gui.TextBox.TextBoxAction;
import com.henry.set.input.Keyboard;
import com.henry.set.util.Const;

public class PlayerNameMenu extends Menu {

	public PlayerNameMenu(Game game) {
		super(game);
		
		Button back = new Button("Go Back", 0, 0, true);
		back.action = new ButtonAction() {
			public void onRelease() {
				textBoxes.clear();
				game.switchTo(GameMode.NUMPLAYERS);
			}
			
			public void onPress() { ; }
		};
		
		Button next = new Button("Start!", 0, back.rect.height, true);
		next.action = new ButtonAction() {
			public void onRelease() {
				tryNext();
			}
			
			public void onPress() { ; } 
		};
		
		buttons.add(back);
		buttons.add(next);
	}
	
	@Override
	public void activate() {
		super.activate();
		
		textBoxes.clear();
		
		int n = game.getNumPlayers();
		int margin = Const.MARGIN_PLAYER_NAME;
		int maxChars = Const.TEXTBOX_MAXCHARS;
		int height = new TextBox("", 0, 0).rect.height;
		int range = height * n + margin * (n - 1);
		int y = Panel.HEIGHT / 2 - range / 2;
		
		for(int i = 0; i < n; i++) {
			textBoxes.add(new TextBox("", "Player " + Integer.toString(i + 1) + " Name", maxChars, Panel.WIDTH / 2, y, false));
			textBoxes.get(i).action = new TextBoxAction() {
				public void onEnter() {
					tryNext();
				}
			};
			y += margin + height;
		}
	}
	
	public void tryNext() {
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < textBoxes.size(); i++) {
			String text = textBoxes.get(i).text.trim();
			if(text == "") {
				Logger.bad("Please enter a name for player " + Integer.toString(i + 1));
				return;
			} else {
				names.add(text);
			}
		}
		
		game.setPlayerNames(names);
	}
	
}
