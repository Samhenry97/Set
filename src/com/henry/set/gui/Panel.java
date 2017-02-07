package com.henry.set.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.henry.set.core.Game;
import com.henry.set.input.Keyboard;
import com.henry.set.input.Mouse;
import com.henry.set.input.ResizeListener;
import com.henry.set.util.Const;
import com.henry.set.util.Util;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable {
	
	public static int WIDTH = Const.WINDOW_WIDTH;
	public static int HEIGHT = Const.WINDOW_HEIGHT;
	
	private Game game;
	private boolean running = false;
	private Thread thread;
	
	public Panel() {
		game = new Game(this);
		
		setPreferredSize(new Dimension(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT));
		Mouse mouse = new Mouse(game);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addKeyListener(new Keyboard(game));
		setFocusTraversalKeysEnabled(false);
		addComponentListener(new ResizeListener(game));
		setBackground(Const.BACKGROUND_COLOR);
		setFocusable(true);
		requestFocusInWindow();
		requestFocus();
		
		start();
	}
	
	public void update() {
		game.update();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.render(g);
		
		g.dispose();
	}
	
	public void run() {
		running = true;
		while(running) {
			update();
			repaint();
			Util.sleep(1);
		}
	}
	
	private void start() {
		thread = new Thread(this, "Panel");
		thread.start();
	}
	
}
