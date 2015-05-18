package com.tripointgames.problematic.util;

/**
 * The difficulty of the game is determined by the user. This influences how
 * challenging math problems are.
 * 
 * @author Faizaan Datoo
 */
public enum Difficulty {

	SuperEasy(5), Easy(10), Medium(20), Hard(50), SuperHard(100);

	int maxNumberValue;

	Difficulty(int maxNumberValue) {
		this.maxNumberValue = maxNumberValue;
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
