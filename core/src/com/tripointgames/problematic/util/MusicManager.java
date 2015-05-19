package com.tripointgames.problematic.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.utils.Array;

/**
 * Manages the background music in the game.
 * 
 * @author Faizaan Datoo
 */
public class MusicManager {

	private Array<Music> musics;
	private Music currentMusic = null;
	private int currentId = -1;
	private float volume = 0.1f;

	public MusicManager() {
		musics = new Array<Music>();
		registerMusic();
		// Start the first track
		if (currentId == -1) {
			getNextTrack();
			return;
		}
	}

	/**
	 * Register all the background music.
	 */
	private void registerMusic() {
		musics.add(AssetManager.getInstance().getMusic("music0"));
		musics.add(AssetManager.getInstance().getMusic("music1"));
		musics.add(AssetManager.getInstance().getMusic("music2"));
	}

	/**
	 * Stop all playing music.
	 */
	public void dispose() {
		currentMusic.stop();
		musics.clear();
	}

	private void getNextTrack() {
		currentId++;
		if (currentId > musics.size - 1) currentId = 0;

		// Get the next music and set it to not loop and set the volume to the
		// set volume above.
		currentMusic = musics.get(currentId);
		currentMusic.setLooping(false);
		currentMusic.setVolume(volume);

		// Add the completion listener, so the music changes to the next when it
		// finishes.
		currentMusic.setOnCompletionListener(completionListener);

		currentMusic.play();
	}

	/** Play the next track when the song completes. */
	OnCompletionListener completionListener = new OnCompletionListener() {
		@Override
		public void onCompletion(Music music) {
			getNextTrack();
		}
	};

}