package com.henry.set.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.henry.set.input.WindowEvents;
import com.henry.set.util.Const;
import com.henry.set.util.Images;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	public Frame() {
		WindowEvents e = new WindowEvents();
		addWindowListener(e);
		addWindowFocusListener(e);
		addWindowStateListener(e);
		
		setIconImage(Images.readImage(("res/images/setIcon.png")));
		setMinimumSize(new Dimension(Const.MIN_WINDOW_HEIGHT, Const.MIN_WINDOW_HEIGHT));
		setTitle("Set");
		Panel p = new Panel();
		setContentPane(p);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
