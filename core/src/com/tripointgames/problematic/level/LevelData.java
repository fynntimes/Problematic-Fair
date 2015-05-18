package com.tripointgames.problematic.level;

/**
 * Stores data about the level. This class is serialized and deserialized
 * into/out of JSON in the LevelManager.
 * 
 * @author Faizaan Datoo
 */
public class LevelData {

	private boolean unlocked = false;

	public boolean isUnlocked() {
		return unlocked;
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

}
