package com.tripointgames.problematic.screens;

import com.badlogic.gdx.Screen;
import com.tripointgames.problematic.AssetManager;
import com.tripointgames.problematic.world.GameWorld;

/**
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameScreen implements Screen {

	private GameWorld world;
	
	@Override
	public void show() {
		world = new GameWorld(AssetManager.getInstance().getMap("testLevel"));
	}

	@Override
	public void render(float delta) {
		world.update();
		world.render();
	}

	@Override
	public void resize(int width, int height) {
		// Resizing shouldn't occur on a mobile device, so don't do anything on
		// resize.
	}

	@Override
	public void pause() {
		// Menu screen can't pause, so don't do anything on pause.
	}

	@Override
	public void resume() {
		// Menu screen can't pause or resume, so don't do anything on resume.
	}

	/**
	 * Called when the screen is no longer being shown.
	 */
	@Override
	public void hide() {
	}

	/**
	 * Called when the game ends and all resources should be disposed.
	 */
	@Override
	public void dispose() {

	}

}
