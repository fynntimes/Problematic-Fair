package com.tripointgames.problematic.util;

import com.badlogic.gdx.math.MathUtils;

/**
 * Generates and stores the number sentence and answers for a math problem. This
 * is used by the MathScreen.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class MathProblem {

	public enum ProblemType {
		Addition, Subtraction, Multiplication;
	}

	// Difficulty determines how high the numbers go in the math problem.
	Difficulty gameDifficulty;

	public String equation;
	int realAnswer;
	public int[] answers;
	ProblemType type;

	public MathProblem() {
		this.gameDifficulty = Difficulty.valueOf(PreferencesManager.getInstance()
				.getString("difficulty"));

		selectType();

		answers = new int[3]; // 3 answers
		generateProblem();
		generateAnswers();
	}

	/**
	 * Check if an answer is correct.
	 * 
	 * @param answerId
	 *            The ID of the answer to check.
	 * @return True if the answer is correct, false otherwise.
	 */
	public boolean validateAnswer(int answerId) {
		return answers[answerId] == realAnswer;
	}

	/**
	 * Randomly select the type of sentence this is.
	 */
	private void selectType() {
		boolean additionEnabled = PreferencesManager.getInstance().getBoolean(
				"showAdditionProblems");
		boolean subtractionEnabled = PreferencesManager.getInstance().getBoolean(
				"showSubtractionProblems");
		boolean multiplicationEnabled = PreferencesManager.getInstance().getBoolean(
				"showMultiplicationProblems");

		// Choose a random value from the ProblemType enum
		ProblemType chosenType = ProblemType.values()[MathUtils.random(2)];

		// Make sure the chosen type is enabled by the user.
		if (chosenType == ProblemType.Addition && !additionEnabled) {
			// If it is not, choose the next available type.
			if (subtractionEnabled) chosenType = ProblemType.Subtraction;
			else if (multiplicationEnabled) chosenType = ProblemType.Multiplication;
		} else if (chosenType == ProblemType.Subtraction && !subtractionEnabled) {
			if (subtractionEnabled) chosenType = ProblemType.Subtraction;
			else if (multiplicationEnabled) chosenType = ProblemType.Multiplication;
		} else if (chosenType == ProblemType.Multiplication && !multiplicationEnabled) {
			if (subtractionEnabled) chosenType = ProblemType.Subtraction;
			else if (multiplicationEnabled) chosenType = ProblemType.Multiplication;
		}

		this.type = chosenType;
	}

	/**
	 * Generate the numbers within the number sentence.
	 */
	private void generateProblem() {
		// Randomly generate the numbers in the sentence, from 1 to the
		// difficulty's max number.
		// Member one has to be larger than number 2, or - and / won't work
		int memberOne = MathUtils.random(2, gameDifficulty.getMaxNumberValue());
		int memberTwo = MathUtils.random(1, gameDifficulty.getMaxNumberValue());

		// Make sure memberOne is higher than memberTwo. If not, swap them.
		if (memberOne < memberTwo) {
			// Swap them
			int tempTwo = memberTwo;
			memberTwo = memberOne;
			memberOne = tempTwo;
		}

		// Get the answer of the sentence
		switch (type) {
		case Addition:
			this.equation = memberOne + " + " + memberTwo;
			this.realAnswer = memberOne + memberTwo;
			break;
		case Subtraction:
			this.equation = memberOne + " - " + memberTwo;
			this.realAnswer = memberOne - memberTwo;
			break;
		case Multiplication:
			this.equation = memberOne + " x " + memberTwo;
			this.realAnswer = memberOne * memberTwo;
			break;
		default: // Unknown operation
			System.err.println("Type of equation is equal to " + type.name()
					+ ". This is not supported.");
			return;
		}
	}

	/**
	 * Generate the answers for this problem. This will choose a random position
	 * for the real answer (0, 1, or 2), and then it will populate the rest of
	 * the array with offset answers.
	 */
	private void generateAnswers() {
		int realAnswerPosition = MathUtils.random(2);
		this.answers[realAnswerPosition] = this.realAnswer;

		// Populate the rest of the list

		// Amount to offset the wrong answers by, from 1-5.
		int randomOffset = MathUtils.random(1, 5);
		if (realAnswerPosition == 0) {
			// Absolute value to avoid negatives.
			this.answers[1] = Math.abs(this.realAnswer - randomOffset);
			this.answers[2] = this.realAnswer + randomOffset;
		} else if (realAnswerPosition == 1) {
			// Absolute value to avoid negatives.
			this.answers[0] = Math.abs(this.realAnswer - randomOffset);
			this.answers[2] = this.realAnswer + randomOffset;
		} else if (realAnswerPosition == 2) {
			// Absolute value to avoid negatives.
			this.answers[0] = Math.abs(this.realAnswer - randomOffset);
			this.answers[1] = this.realAnswer + randomOffset;
		}
	}

}
