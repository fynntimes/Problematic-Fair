package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Loads and manages levels.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class LevelManager {

	private Array<Level> levels;

	public LevelManager() {
		levels = new Array<Level>();
	}

	public void loadLevels() {
		// This loads all the levels in the maps file.
		int currentId = 0;
		while (true) {
			// Load the next level. If loadLevel returns false, there are no
			// more maps. Exit the loop.
			if (loadLevel(currentId) == false)
				break;
			currentId++; // Increment currentId so that the next level can be loaded.
		}
		levels.get(0).levelData.unlocked = true;
		levels.get(0).levelData.starsEarned = 3;
		levels.get(0).save();
	}

	/**
	 * Load in a level from the maps/ directory.
	 * 
	 * @param id
	 *            The ID of the level to load
	 * @return true if the level was successfully loaded, false otherwise.
	 */
	private boolean loadLevel(int id) {
		FileHandle levelHandle = Gdx.files.internal("maps/level" + id + ".tmx");
		if (!levelHandle.exists())
			return false;
		AssetManager.getInstance().registerMap("level" + id,
				"maps/level" + id + ".tmx");
		levels.add(new Level("level" + id));
		return true;
	}

	public Level getLevel(int id) {
		id = id - 1;
		// Avoids an ArrayIndexOutOfBoundsException.
		if (id > levels.size - 1)
			return null;
		return levels.get(id);
	}

	public boolean isLevel(int id) {
		return getLevel(id) != null;
	}

}
