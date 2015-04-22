package com.tripointgames.problematic;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The screen which renders and handles input for the main menu.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class MenuScreen implements Screen {

	private SpriteBatch menuSpriteBatch;

	@Override
	public void show() {
		// SpriteBatch is a list of objects being sent to OpenGL at once. It is
		// part of LibGDX.
		menuSpriteBatch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		menuSpriteBatch.begin(); // Start rendering

		menuSpriteBatch.end(); // Finish rendering
	}

	@Override
	public void resize(int width, int height) {
		// Resizing shouldn't occur on a mobile device.
	}

	@Override
	public void pause() {
		// Menu screen can't pause
	}

	@Override
	public void resume() {
		// Menu screen can't pause or resume
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
