package com.henry.set.main;

import javax.swing.SwingUtilities;

import com.henry.set.gui.Frame;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Frame();
			}
		});
	}
}
