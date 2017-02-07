package com.henry.set.gui.menu;

import com.henry.set.core.Game;
import com.henry.set.core.Game.GameMode;
import com.henry.set.gui.Button;
import com.henry.set.gui.Button.ButtonAction;
import com.henry.set.gui.Panel;
import com.henry.set.util.Const;

public class NumPlayersMenu extends Menu {

	public NumPlayersMenu(Game game) {
		super(game);
		
		int total = 8;
		
		int startX = (int) (Panel.WIDTH * Const.MARGIN_BUTTON);
		int rangeX = Panel.WIDTH - startX * 2;
		int buttonWidth = new Button("1", 0, 0).rect.width;
		int buttonHeight = new Button("1", 0, 0).rect.height;
		int buttonMarginX = (int) ((rangeX - (buttonWidth * total / 2)) / (total / 2 - 1));
		
		int x = startX;
		int y = startX;
		int cur = 1;
		for(int i = 0; i < total / 2; i++) {
			final int num = cur;
			Button b = new Button(Integer.toString(cur), x + buttonWidth / 2, y + buttonHeight / 2);
			b.action = new ButtonAction() {
				public void onRelease() {
					game.setNumPlayers(num);
				}
				
				public void onPress() { ; }
			};
			buttons.add(b);
			x += buttonWidth + buttonMarginX;
			cur++;
		}
		
		x = startX;
		y = Panel.HEIGHT - startX - buttonHeight;
		for(int i = 0; i < total / 2; i++) {
			final int num = cur;
			Button b = new Button(Integer.toString(cur), x + buttonWidth / 2, y + buttonHeight / 2);
			b.action = new ButtonAction() {
				public void onRelease() {
					game.setNumPlayers(num);
				}
				
				public void onPress() { ; }
			};
			buttons.add(b);
			x += buttonWidth + buttonMarginX;
			cur++;
		}
		
		Button b = new Button("Go Back", 0, 0, true);
		b.action = new ButtonAction() {
			public void onRelease() {
				game.switchTo(GameMode.START);
			}
			
			public void onPress() { ; }
		};
		buttons.add(b);
	}
	
}
