package com.henry.set.input;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.henry.set.core.Game;
import com.henry.set.gui.Panel;

public class ResizeListener implements ComponentListener {

	private Game game;
	
	public ResizeListener(Game game) {
		this.game = game;
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Panel.WIDTH = e.getComponent().getWidth();
		Panel.HEIGHT = e.getComponent().getHeight();
		
		game.updatePositions();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

}
