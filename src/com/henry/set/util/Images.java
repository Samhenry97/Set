package com.henry.set.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {
	
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	
	public static void addImage(String name) {
		if(!images.containsKey(name)) {
			images.put(name, readImage("res/images/" + name + ".png"));
		}
	}
	
	public static void addImage(String name, boolean extension) {
		if(!images.containsKey(name)) {
			images.put(name, readImage("res/images/" + name));
		}
	}
	
	public static Image readImage(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch(IOException e) {
			System.err.println("Could not load image: " + fileName);
			return null;
		}
	}
	
	public static Image get(String name) {
		return images.get(name);
	}

}
