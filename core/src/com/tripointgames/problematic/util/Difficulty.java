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

	/**
	 * @param id
	 *            The ID of this difficulty value, used by the slider in
	 *            OptionsScreen
	 * @param maxNumberValue
	 *            The max random number that can be generated in MathProblem
	 *            (The higher the number the harder the problem.)
	 */
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
