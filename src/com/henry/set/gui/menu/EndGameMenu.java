package com.henry.set.gui.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.henry.set.core.Game;
import com.henry.set.core.Game.GameMode;
import com.henry.set.core.Player;
import com.henry.set.gui.Button;
import com.henry.set.gui.Panel;
import com.henry.set.gui.Button.ButtonAction;
import com.henry.set.util.Const;

public class EndGameMenu extends Menu {
	
	private ArrayList<Player> maxPlayers = new ArrayList<Player>();

	public EndGameMenu(Game game) {
		super(game);
		
		Button newGame = new Button("New Game", 0, 0, true);
		newGame.action = new ButtonAction() {
			public void onRelease() {
				game.switchTo(GameMode.NUMPLAYERS);
			}
			
			public void onPress() { ; }
		};
		
		buttons.add(newGame);
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		g.setFont(Const.PLAYER_FONT);
		if(maxPlayers.size() == 1) {
			g.setColor(Color.green);
			g.drawString(maxPlayers.get(0).name + " won!", Panel.WIDTH / 2, 50);
		} else {
			g.setColor(Color.green);
			g.drawString("It was a tie between " + Integer.toString(maxPlayers.size()) + " players!", Panel.WIDTH / 2 - 100, 50);
			
			int y = 100;
			for(Player p : maxPlayers) {
				g.drawString(p.name, Panel.WIDTH / 2 - 50, y);
				y += 50;
			}
		}
	}
	
	@Override
	public void activate() {
		super.activate();
		
		maxPlayers = game.getTopPlayers();
	}
	
}
