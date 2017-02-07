package com.henry.set.util;

import java.awt.Color;
import java.awt.Font;

public class Const {
	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;
	public static int MIN_WINDOW_WIDTH = 640;
	public static int MIN_WINDOW_HEIGHT = 480;
	
	public static int BOARD_SIZE = 12;
	
	public static int TEXTBOX_MAXCHARS = 20;
	
	public static float MARGIN_X = 2f/10f;
	public static float MARGIN_Y = 1f/10f;
	public static float MARGIN_CARD = 1f/20f;
	public static float MARGIN_PLAYER = 1f/20f;
	public static float MARGIN_BUTTON = 1f/4f;
	public static float MARGIN_BETWEEN_BUTTON = 1f/20f;
	public static int MARGIN_PLAYER_NAME = 20;
	public static int MARGIN_LOGGER = 20;
	public static int MARGIN_MENU_BUTTON = 10;
	
	public static int LOG_TIME = 2000;
	
	public static int BUTTON_PADDING = 20;
	public static int TEXTBOX_PADDING = 10;
	public static int PLAYER_PADDING = 10;
	
	public static Color BACKGROUND_COLOR = new Color(100, 100, 100);
	public static Color PLAYER_COLOR = new Color(15, 125, 0);
	public static Color BUTTON_COLOR = new Color(0, 100, 0);
	public static Color BUTTON_HOVER = new Color(0, 150, 0);
	public static Color TEXTBOX_COLOR = new Color(150, 50, 0);
	public static Color TEXTBOX_HOVER = new Color(175, 75, 25);
	public static Color TEXTBOX_ACTIVE = new Color(150, 150, 150, 150);
	public static Color TEXTBOX_BORDER_COLOR = new Color(100, 0, 100, 200);
	public static Color PAUSE_COLOR = new Color(50, 50, 50, 200);
	
	public static Font PLAYER_FONT = new Font("Arial", Font.BOLD, 20);
	public static Font BUTTON_FONT = new Font("Arial", Font.ITALIC, 20);
	public static Font TEXTBOX_FONT = new Font("Arial", Font.PLAIN, 20);
	public static Font LOG_FONT = new Font("Arial", Font.BOLD | Font.ITALIC, 30);
}
