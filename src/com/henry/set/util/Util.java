package com.henry.set.util;

public class Util {
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
