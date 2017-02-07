package com.henry.set.gui.menu;

import com.henry.set.core.Game;
import com.henry.set.core.Game.GameMode;
import com.henry.set.gui.Button;
import com.henry.set.gui.Button.ButtonAction;
import com.henry.set.gui.Logger;
import com.henry.set.gui.Panel;
import com.henry.set.util.Const;

public class InGameMenu extends Menu {

	public InGameMenu(Game game) {
		super(game);
		
		int n = 4;
		int margin = Const.MARGIN_MENU_BUTTON;
		int height = new Button("", 0, 0, false).rect.height;
		int range = height * n + margin * (n - 1);
		int y = Panel.HEIGHT / 2 - range / 2;
		int x = Panel.WIDTH / 2;
		
		Button setExists = new Button("Cheat", x, y);
		setExists.action = new ButtonAction() {
			public void onRelease() {
				if(game.setExistsOnBoard()) {
					Logger.good("Set Exists!");
				} else {
					Logger.bad("No Set Exists.");
				}
			}
			
			public void onPress() { ; }
		};
		
		y += margin + height;
		
		Button showSets = new Button("Show Sets", x, y);
		showSets.action = new ButtonAction() {
			public void onRelease() {
				game.showSetsOnBoard();
			}
			
			public void onPress() { ; }
		};
		
		y += margin + height;
		
		Button shuffle = new Button("Shuffle", x, y);
		shuffle.action = new ButtonAction() {
			public void onRelease() {
				game.shuffleCards();
			}
			
			public void onPress() { ; }
		};
		
		y += margin + height;
		
		Button endGame = new Button("Finish", x, y);
		endGame.action = new ButtonAction() {
			public void onRelease() {
				game.switchTo(GameMode.ENDGAME);
			}
			
			public void onPress() { ; }
		};

		buttons.add(setExists);
		buttons.add(showSets);
		buttons.add(shuffle);
		buttons.add(endGame);
	}
	
}
