package com.tripointgames.problematic.util;

/**
 * The difficulty of the game is determined by the user. This influences how
 * challenging math problems are.
 * 
 * @author Faizaan Datoo
 */
public enum Difficulty {

	SuperEasy(0, 5), Easy(1, 10), Medium(2, 20), Hard(3, 50), SuperHard(4, 100);

	int id;
	int maxNumberValue;

	Difficulty(int id, int maxNumberValue) {
		this.id = id;
		this.maxNumberValue = maxNumberValue;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getMaxNumberValue() {
		return maxNumberValue;
	}

	@Override
	public String toString() {
		// The toString value is used to save the difficulty, make it look nice.
		return this.name();
	}

}
