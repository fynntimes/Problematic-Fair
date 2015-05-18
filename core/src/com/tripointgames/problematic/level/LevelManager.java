package com.tripointgames.problematic.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.tripointgames.problematic.util.AssetManager;

/**
 * Loads and manages levels.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class LevelManager {

	private Array<Level> levels;

	private Level currentLevel = null;

	public LevelManager() {
		levels = new Array<Level>();
	}

	public void loadLevels() {
		// This loads all the levels in the maps file.
		int currentId = 0;
		while (loadLevel(currentId)) {
			currentId++;
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
		
		// Testing stuff
//		getLevel(id + 1).levelData.setUnlocked(true);
//		getLevel(id + 1).save();
		
		return true;
	}

	public Level getLevel(int id) {
		id = id - 1; // Subtract one to get an index starting at 0.
		// Avoids an ArrayIndexOutOfBoundsException.
		if (id > levels.size - 1) return null;
		return levels.get(id);
	}

	public boolean isLevel(int id) {
		return getLevel(id) != null;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * Dispose of all levels.
	 */
	public void dispose() {
		for (Level level : levels)
			level.dispose();
	}

}
