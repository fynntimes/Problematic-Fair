package com.tripointgames.problematic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Saves, manages, and changes game options. This class is a singleton, so it
 * can be accessed statically.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class PreferencesManager {

	private HashMap<String, String> preferences; // <Preference, Value>
	private FileHandle preferencesFile;

	private static PreferencesManager instance;

	public static PreferencesManager getInstance() {
		if (instance == null) instance = new PreferencesManager();
		return instance;
	}

	protected PreferencesManager() {
		preferences = new HashMap<String, String>();
		// Local file "preferences.txt" (i.e. stored in the app's root folder)
		preferencesFile = Gdx.files.local("preferences.txt");
	}

	/**
	 * Set a preference to a specific value. If the preference already exists,
	 * it will be overwritten.
	 * 
	 * @param preference
	 *            The name (key) of the preference.
	 * @param value
	 *            The value to assign the preference.
	 */
	public void set(String preference, Object value) {
		if (preferences.get(preference) != null) preferences.remove(preference);
		preferences.put(preference, value.toString());
	}

	/**
	 * Get a string from the game preferences.
	 * 
	 * @param preference
	 *            The name (key) of the preference.
	 * @return The preference's value, as a String.
	 */
	public String getString(String preference) {
		return preferences.get(preference);
	}

	/**
	 * Get a float from the game preferences.
	 * 
	 * @param preference
	 *            The name (key) of the preference.
	 * @return The preference's value, parsed to a float.
	 */
	public float getFloat(String preference) {
		return Float.parseFloat(preferences.get(preference));
	}

	/**
	 * Get a boolean from the game preferences.
	 * 
	 * @param preference
	 *            The name (key) of the preference.
	 * @return The preference's value, parsed to a boolean.
	 */
	public boolean getBoolean(String preference) {
		return Boolean.parseBoolean(preferences.get(preference));
	}

	/**
	 * Checks if a preference is already set in the list.
	 * 
	 * @param preference
	 *            The name (key) of the preference.
	 * @return True if the preferences list contains the preference, false
	 *         otherwise.
	 */
	public boolean isSet(String preference) {
		return preferences.containsKey(preference);
	}

	/**
	 * Load the preferences from the local file "preferences.txt". If it does
	 * not exist, it will be created first.
	 * 
	 * @throws IOException
	 *             If the reader could not read a line from the file
	 *             successfully, or if the file couldn't be found.
	 */
	public void loadPreferences() throws IOException {
		if (!preferencesFile.exists()) savePreferences(); // Create file

		BufferedReader fileReader = new BufferedReader(new InputStreamReader(
				preferencesFile.read()));
		String currentLine;
		while ((currentLine = fileReader.readLine()) != null) {
			String[] tokens = currentLine.split("=");
			preferences.put(tokens[0], tokens[1]);
		}
	}

	/**
	 * Checks if the preferences file contains all the preferences needed by the
	 * game. If the preference could not be found, it will be added.
	 */
	public void checkDefaults() {
		if (!isSet("showAdditionProblems")) {
			set("showAdditionProblems", true);
		}
		if (!isSet("showSubtractionProblems")) {
			set("showSubtractionProblems", true);
		}
		if (!isSet("showMultiplicationProblems")) {
			set("showMultiplicationProblems", true);
		}
		if (!isSet("showDivisionProblems")) {
			set("showDivisionProblems", true);
		}
		if (!isSet("difficulty")) {
			set("difficulty", Difficulty.Easy);
		}
		savePreferences();
	}

	/**
	 * Save the preferences to the local file "preferences.txt". Lines are
	 * written like this: preference=value, followed by the system's new line
	 * symbol (usually \n).
	 */
	public void savePreferences() {
		StringBuilder preferenceFileContents = new StringBuilder();
		for (String key : preferences.keySet()) {
			preferenceFileContents.append(key + "=" + preferences.get(key)).append(
					System.lineSeparator());
		}
		preferencesFile.writeString(preferenceFileContents.toString(), false);
	}

}
