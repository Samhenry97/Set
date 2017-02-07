package com.henry.set.core;

public class Set {

	public Card first;
	public Card second;
	public Card third;
	
	public Set(Card a, Card b, Card c) {
		first = a;
		second = b;
		third = c;
	}
	
	public boolean valid() {
		boolean shade, color, number, shape;
		shade = color = number = shape = false;
		
		if(first.shade == second.shade && second.shade == third.shade) shade = true;
		else if(first.shade != second.shade && second.shade != third.shade && first.shade != third.shade) shade = true;
		
		if(first.color == second.color && second.color == third.color) color = true;
		else if(first.color != second.color && second.color != third.color && first.color != third.color) color = true;
		
		if(first.number == second.number && second.number == third.number) number = true;
		else if(first.number != second.number && second.number != third.number && first.number != third.number) number = true;
		
		if(first.shape == second.shape && second.shape == third.shape) shape = true;
		else if(first.shape != second.shape && second.shape != third.shape && first.shape != third.shape) shape = true;
		
		return (shade && color && number && shape);
	}
	
}
