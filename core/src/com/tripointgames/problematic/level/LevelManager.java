package com.tripointgames.problematic.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.tripointgames.problematic.util.AssetManager;

/**
 * Loads and manages levels.
 * 
 * @author Faizaan Datoo
 */
public class LevelManager {

	private Array<Level> levels;

	private Level currentLevel = null;
	private int currentLevelID = 0;

	public LevelManager() {
		levels = new Array<Level>();
	}

	/**
	 * Loops through all .TMX (map) files in the internal "maps" directory and
	 * loads them in. When there are no more files, the loop finishes.
	 */
	public void loadLevels() {
		// This loads all the levels in the maps file.
		int currentId = 0;

		// Load level returns false when the level file does not exist.
		while (loadLevel(currentId)) {
			currentId++; // Load the next level ID
		}
		// This makes sure the first level is always unlocked.
		getLevel(1).levelData.setUnlocked(true);
		getLevel(1).save();
		currentLevel = getLevel(1); // TODO Remove this
	}

	/**
	 * Load in a level from the "/assets/maps/" directory.
	 * 
	 * @param id
	 *            The ID of the level to load
	 * @return true if the level was successfully loaded, false otherwise.
	 */
	private boolean loadLevel(int id) {
		FileHandle levelHandle = Gdx.files.internal("maps/level" + id + ".tmx");
		if (!levelHandle.exists()) // Check if the level exists first
			return false;
		AssetManager.getInstance().registerMap("level" + id,
				"maps/level" + id + ".tmx");
		levels.add(new Level("level" + id));

		getLevel(id + 1).levelData.setUnlocked(true);
		getLevel(id + 1).save();

		return true;
	}

	/**
	 * Retrieve a level from the LevelManager.
	 * 
	 * @param id
	 *            The ID of this level (starting at 1, not 0)
	 * @return The level if it was found or null.
	 */
	public Level getLevel(int id) {
		id = id - 1; // Subtract one to get an index starting at 0.
		// Avoids an ArrayIndexOutOfBoundsException.
		if (id > levels.size - 1) return null;
		return levels.get(id);
	}

	/**
	 * Checks if a level with the passed ID is loaded.
	 * 
	 * @param id
	 *            The ID of the level to check for.
	 * @return True if the level is loaded, false otherwise.
	 */
	public boolean isLevel(int id) {
		return getLevel(id) != null;
	}

	/**
	 * Get the current level the game is on.
	 * 
	 * @return The current level, or null if the game has not yet entered the
	 *         GameScreen.
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Set the current level.
	 * 
	 * @param currentLevel
	 *            The level to set as current, or null if the game has exited
	 *            the GameScreen.
	 * @param id
	 *            The ID of the level to set as current.
	 */
	public void setCurrentLevel(Level currentLevel, int id) {
		this.currentLevel = currentLevel;
		this.currentLevelID = id;
	}

	/**
	 * Change the level to the next one.
	 */
	public void incrementLevel() {
		this.currentLevelID++;
		this.currentLevel = getLevel(currentLevelID);
	}

	/**
	 * Dispose of all levels and their resources
	 */
	public void dispose() {
		for (Level level : levels)
			level.dispose();
	}

}
