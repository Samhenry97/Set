package com.henry.set.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class WindowEvents implements WindowListener, WindowFocusListener, WindowStateListener {

	@Override
	public void windowStateChanged(WindowEvent e) {
		
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		Mouse.clearButtons();
		Keyboard.clearKeys();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		Mouse.clearButtons();
		Keyboard.clearKeys();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

}
