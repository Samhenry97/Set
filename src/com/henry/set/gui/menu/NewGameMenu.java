package com.henry.set.gui.menu;

import com.henry.set.core.Game;
import com.henry.set.core.Game.GameMode;
import com.henry.set.gui.Button;
import com.henry.set.gui.Button.ButtonAction;
import com.henry.set.util.Const;

public class NewGameMenu extends Menu {

	public NewGameMenu(Game game) {
		super(game);
		
		Button newGame = new Button("New Game", Const.WINDOW_WIDTH / 2, Const.WINDOW_HEIGHT / 2);
		newGame.action = new ButtonAction() {
			public void onRelease() {
				game.switchTo(GameMode.NUMPLAYERS);
			}
			
			public void onPress() { ; }
		};
		buttons.add(newGame);
	}
	
}
