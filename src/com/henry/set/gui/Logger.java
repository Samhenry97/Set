package com.henry.set.gui;

import java.awt.Graphics;
import java.util.ArrayList;

public class Logger {
	
	private static ArrayList<Log> goodLogs;
	private static ArrayList<Log> badLogs;

	public static void good(String message) {
		goodLogs.add(new Log(message, true));
	}
	
	public static void bad(String message) {
		badLogs.add(new Log(message, false));
	}
	
	public static void remove(Log log) {
		if(log.good) {
			goodLogs.remove(log);
		} else {
			badLogs.remove(log);
		}
	}
	
	//*********************************************************
	//*********************************************************
	
	public Logger() {		
		goodLogs = new ArrayList<Log>();
		badLogs = new ArrayList<Log>();
	}
	
	public void update() {
		for(int i = 0; i < goodLogs.size(); i++) {
			goodLogs.get(i).update();
		}
		for(int i = 0; i < badLogs.size(); i++) {
			badLogs.get(i).update();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < goodLogs.size(); i++) {
			goodLogs.get(i).render(g);
		}
		for(int i = 0; i < badLogs.size(); i++) {
			badLogs.get(i).render(g);
		}
	}
	
}
