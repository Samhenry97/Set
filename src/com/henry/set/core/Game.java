package com.henry.set.core;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.henry.set.core.Card.Color;
import com.henry.set.core.Card.Number;
import com.henry.set.core.Card.Shade;
import com.henry.set.core.Card.Shape;
import com.henry.set.gui.Logger;
import com.henry.set.gui.Panel;
import com.henry.set.gui.menu.EndGameMenu;
import com.henry.set.gui.menu.InGameMenu;
import com.henry.set.gui.menu.NewGameMenu;
import com.henry.set.gui.menu.NumPlayersMenu;
import com.henry.set.gui.menu.PlayerNameMenu;
import com.henry.set.input.Keyboard;
import com.henry.set.input.Mouse;
import com.henry.set.util.Const;
import com.henry.set.util.Images;

public class Game implements Keyboard.Typeable {
	
	public static enum GameMode { START, NUMPLAYERS, PLAYERNAMES, INGAME, MENU, ENDGAME };
	
	private Logger logger;
	private Random rand;
	private ArrayList<Card> cards;
	private ArrayList<Card> gameBoard;
	private ArrayList<Card> selected;
	private ArrayList<Player> players;
	private int numPlayers;
	private Player selectedPlayer;
	private GameMode mode = GameMode.START;
	private String backImage = "back3.jpg";
	
	private NewGameMenu newGameMenu;
	private NumPlayersMenu numPlayersMenu;
	private PlayerNameMenu playerNameMenu;
	private InGameMenu inGameMenu;
	private EndGameMenu endGameMenu;
	
	public Game(Panel panel) {
		Keyboard.subscribe(this);
		Images.addImage(backImage, true);
		
		logger = new Logger();
		rand = new Random();
		cards = new ArrayList<Card>();
		gameBoard = new ArrayList<Card>();
		selected = new ArrayList<Card>();
		players = new ArrayList<Player>();
		newGameMenu = new NewGameMenu(this);
		numPlayersMenu = new NumPlayersMenu(this);
		inGameMenu = new InGameMenu(this);
		endGameMenu = new EndGameMenu(this);
		playerNameMenu = new PlayerNameMenu(this);
		selectedPlayer = null;
		numPlayers = 0;
		
		newGameMenu.activate();
		
		generateCards();
		updatePositions();
	}
	
	
	public void update() {
		switch(mode) {
		case START:
			newGameMenu.update();
			break;
			
		case NUMPLAYERS:
			numPlayersMenu.update();
			break;
			
		case PLAYERNAMES:
			playerNameMenu.update();
			break;
			
		case INGAME:
			for(Card c : gameBoard) { c.update(); }
			for(Player p : players) { p.update(); }
			break;
			
		case MENU:
			inGameMenu.update();
			break;
			
		case ENDGAME:
			endGameMenu.update();
			break;
		}
		
		logger.update();
	}
	
	public void render(Graphics g) {
		g.drawImage(Images.get(backImage), 0, 0, Panel.WIDTH, Panel.HEIGHT, null);
		
		switch(mode) {
		case START:
			newGameMenu.render(g);
			break;
			
		case NUMPLAYERS:
			numPlayersMenu.render(g);
			break;
			
		case PLAYERNAMES:
			playerNameMenu.render(g);
			break;
			
		case INGAME: 
			for(Card c : gameBoard) { c.render(g); }
			for(Player p : players) { p.render(g); }
			break;
			
		case MENU:
			for(Card c : gameBoard) { c.render(g); }
			for(Player p : players) { p.render(g); }
			g.setColor(Const.PAUSE_COLOR);
			g.fillRect(0, 0, Panel.WIDTH, Panel.HEIGHT);
			inGameMenu.render(g);
			break;
			
		case ENDGAME:
			endGameMenu.render(g);
			break;
		}
		
		logger.render(g);
	}
	
	public void onKeyPress(int code, char letter) {
		if(code == KeyEvent.VK_ESCAPE) {
			if(mode == GameMode.INGAME) {
				switchTo(GameMode.MENU);
			} else if(mode == GameMode.MENU) {
				switchTo(GameMode.INGAME);
			}
		} else if(code == KeyEvent.VK_SPACE) {
			clearInputs();
		} else if(code == KeyEvent.VK_A) {
			addColumn();
		}
	}
	
	public void onBackspace() { ; }
	
	public void setNumPlayers(int n) {
		numPlayers = n;
		
		switchTo(GameMode.PLAYERNAMES);
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	public void setPlayerNames(ArrayList<String> names) {
		players.clear();
		
		for(int i = 0; i < numPlayers; i++) {
			players.add(new Player(this, names.get(i)));
		}
		
		newGame();
	}
	
	public ArrayList<Player> getTopPlayers() {
		ArrayList<Player> ret = new ArrayList<Player>();
		int maxSets = 0;
		
		for(Player p : players) {
			int sets = p.sets.size();
			if(sets > maxSets) {
				maxSets = sets;
				ret.clear();
				ret.add(p);
			} else if(sets == maxSets) {
				ret.add(p);
			}
		}
		
		return ret;
	}
	
	public void newGame() {		
		cards.clear();
		gameBoard.clear();
		selected.clear();
		
		generateCards();
		createBoard();
		updatePositions();
		
		switchTo(GameMode.INGAME);
	}
	
	public void testForSet() {
		Card c1 = selected.get(0);
		Card c2 = selected.get(1);
		Card c3 = selected.get(2);
		
		if(new Set(c1, c2, c3).valid()) { // We have a set!
			selectedPlayer.addSet(new Set(c1, c2, c3));
			int pos1 = gameBoard.indexOf(c1), pos2 = gameBoard.indexOf(c2), pos3 = gameBoard.indexOf(c3);
			gameBoard.remove(c1); gameBoard.remove(c2);	gameBoard.remove(c3);
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(pos1);
			list.add(pos2);
			list.add(pos3);
			Collections.sort(list);
			updateBoard(list);
			Logger.good(selectedPlayer.name + " found a set!");
		} else {
			Logger.bad("Come on. You're lame. That's NOT a set.");
		}
		
		for(Card c : selected) {
			c.selected = false;
		}
		selected.clear();
		
		selectedPlayer.deselect();
		selectedPlayer = null;
	}
	
	public boolean setExistsOnBoard() {
		return getSetsOnBoard().size() != 0;
	}
	
	public void showSetsOnBoard() {
		ArrayList<Set> sets = getSetsOnBoard();
		
		for(Set s : sets) {
			s.first.highlight();
			s.second.highlight();
			s.third.highlight();
		}
	}
	
	public ArrayList<Set> getSetsOnBoard() {
		ArrayList<Set> ret = new ArrayList<Set>();
		
		for(int i = 0; i < gameBoard.size(); i++) {
			for(int j = i + 1; j < gameBoard.size(); j++) {
				for(int k = j + 1; k < gameBoard.size(); k++) {
					Card c1 = gameBoard.get(i);
					Card c2 = gameBoard.get(j);
					Card c3 = gameBoard.get(k);
					
					Set s = new Set(c1, c2, c3);
					if(s.valid()) {
						ret.add(s);
					}
				}
			}
		}
		
		return ret;
	}
	
	public void clicked() {
		switch(mode) {
		case START:
			break;
			
		case NUMPLAYERS:
			break;
			
		case PLAYERNAMES:
			break;
			
		case INGAME:
			if(selectedPlayer != null) {
				for(Card c : gameBoard) {
					if(c.rect.contains(Mouse.x, Mouse.y)) {
						if(c.selected) {
							selected.remove(c);
							c.selected = false;
						} else {
							selected.add(c);
							c.selected = true;
						}
					}
				}
				if(selected.size() == 3) {
					testForSet();
				}
			} else {
				for(Player p : players) {
					if(p.rect.contains(Mouse.x, Mouse.y)) {
						selectedPlayer = p;
					}
				}
				
				if(selectedPlayer == null) {
					for(Card c : gameBoard) {
						if(c.rect.contains(Mouse.x, Mouse.y)) {
							Logger.bad("Please select a player!");
						}
					}
				}
			}
			break;
			
		case MENU:
			break;
			
		case ENDGAME:
			break;
		}
	}
	
	public void updatePositions() {
		newGameMenu.updatePositions();
		numPlayersMenu.updatePositions();
		playerNameMenu.updatePositions();
		inGameMenu.updatePositions();
		endGameMenu.updatePositions();

		if(gameBoard.size() > 0) {
			int cardsPerRow = gameBoard.size() / 3;
			
			int startX = (int) (Panel.WIDTH * Const.MARGIN_X);
			int rangeX = Panel.WIDTH - (startX * 2);
			int cardMarginX = (int) (rangeX * Const.MARGIN_CARD);
			int cardWidth = (rangeX - (cardMarginX * (cardsPerRow - 1))) / cardsPerRow;
			
			int startY = (int) (Panel.HEIGHT * Const.MARGIN_Y);
			int rangeY = Panel.HEIGHT - (startY * 2);
			int cardMarginY = (int) (rangeY * Const.MARGIN_CARD);
			int cardHeight = (rangeY - (cardMarginY * 2)) / 3;
			
			int x = startX, y = startY;
			int card = 0;
			
			for(int i = 0; i < cardsPerRow; i++) {
				Card c = gameBoard.get(card + i);
				c.rect.setBounds(x, y, cardWidth, cardHeight);
				x += cardWidth + cardMarginX;
			}
			
			card += cardsPerRow;
			y += cardHeight + cardMarginY;
			x = startX;
			for(int i = 0; i < cardsPerRow; i++) {
				Card c = gameBoard.get(card + i);
				c.rect.setBounds(x, y, cardWidth, cardHeight);
				x += cardWidth + cardMarginX;
			}
			
			card += cardsPerRow;
			y += cardHeight + cardMarginY;
			x = startX;
			for(int i = 0; i < cardsPerRow; i++) {
				Card c = gameBoard.get(card + i);
				c.rect.setBounds(x, y, cardWidth, cardHeight);
				x += cardWidth + cardMarginX;
			}
		}
		
		if(players.size() > 0) {
			int startX = (int) (Panel.WIDTH * Const.MARGIN_X);
			int rangeX = Panel.WIDTH - (startX * 2);
			int playerMarginX = (int) (rangeX * Const.MARGIN_PLAYER);
			int playerWidth = (rangeX - (playerMarginX * (players.size() - 1))) / players.size();
			
			int x = startX;
			int y = 0;
			for(int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				p.rect.setBounds(x, y, playerWidth, 40);
				p.calcTextOffset();
				x += playerWidth + playerMarginX;
			}
		}
	}
	
	public void switchTo(GameMode newMode) {
		switch(mode) {
		case START:
			newGameMenu.deactivate(); break;
		case NUMPLAYERS:
			numPlayersMenu.deactivate(); break;
		case PLAYERNAMES:
			playerNameMenu.deactivate(); break;
		case INGAME:
			 break;
		case MENU:
			inGameMenu.deactivate(); break;
		case ENDGAME:
			endGameMenu.deactivate(); break;
		}
		
		mode = newMode;
		switch(mode) { 
		case START:
			newGameMenu.activate(); break;
		case NUMPLAYERS:
			numPlayersMenu.activate(); break;
		case PLAYERNAMES:
			playerNameMenu.activate(); break;
		case INGAME:
			break;
		case MENU:
			inGameMenu.activate(); break;
		case ENDGAME:
			endGameMenu.activate(); break;
		}
	}
	
	public void clearInputs() {
		if(selectedPlayer != null) { selectedPlayer.deselect(); }
		selectedPlayer = null;
		
		for(Card c : selected) { c.selected = false; }
		selected.clear();
		
		for(Card c : gameBoard) { c.highlighted = false; }
	}
	
	private void updateBoard(ArrayList<Integer> positions) {
		if(cards.size() == 0) {
			Logger.bad("No more cards!");
			Logger.good("Game Over!");
			return;
		}
		
		if(gameBoard.size() < 12) {
			int index = rand.nextInt(cards.size());
			gameBoard.add(positions.get(0), cards.get(index));
			cards.remove(index);
			index = rand.nextInt(cards.size());
			gameBoard.add(positions.get(1), cards.get(index));
			cards.remove(index);
			index = rand.nextInt(cards.size());
			gameBoard.add(positions.get(2), cards.get(index));
			cards.remove(index);
		}
		
		updatePositions();
	}
	
	private void createBoard() {		
		while(gameBoard.size() < 12) {
			int index = rand.nextInt(cards.size());
			gameBoard.add(cards.get(index));
			cards.remove(index);
		}
		
		updatePositions();
	}
	
	public void addColumn() {
		if(cards.size() == 0) {
			Logger.bad("No cards left!");
			return;
		}
		
		if(gameBoard.size() == 12) {
			int index = rand.nextInt(cards.size());
			gameBoard.add(cards.get(index));
			cards.remove(index);
			index = rand.nextInt(cards.size());
			gameBoard.add(8, cards.get(index));
			cards.remove(index);
			index = rand.nextInt(cards.size());
			gameBoard.add(4, cards.get(index));
			cards.remove(index);
		} else {
			while(gameBoard.size() < 15) {
				int index = rand.nextInt(cards.size());
				gameBoard.add(cards.get(index));
				cards.remove(index);
			}
		}
		
		updatePositions();
	}
	
	private void generateCards() {
		cards.clear();
		
		for(Shape s : Card.shapes) {
			for(Color c : Card.colors) {
				for(Number n : Card.numbers) {
					for(Shade h : Card.shades) {
						cards.add(new Card(s, c, n, h));
					}
				}
			}
		}
	}
	
	public void selectPlayer(Player p) {
		if(selectedPlayer != p) { selectedPlayer.deselect(); }
		selectedPlayer = p;
	}
	
	public void shuffleCards() {
		Collections.shuffle(gameBoard);
		updatePositions();
	}

}
