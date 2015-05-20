package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tripointgames.problematic.util.AssetManager;

/**
 * The HelpScreen displays an image with text explaining how to use the
 * on-screen GUI.
 * 
 * @author Faizaan Datoo
 */
public class HelpScreen implements Screen {

	Main gameInstance; // Instance of the main class

	SpriteBatch spriteBatch; // SpriteBatch is basically a LibGDX renderer.
	Texture helpScreen;

	public HelpScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		helpScreen = AssetManager.getInstance().getTexture("helpscreen");
	}

	@Override
	public void render(float delta) {
		checkForInput();

		// Draw the help screen image to fill the screen
		spriteBatch.begin();
		spriteBatch.draw(helpScreen, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		spriteBatch.end();
	}

	@Override
	public void dispose() {
	}

	/**
	 * Check for input submitted by the user.
	 */
	private void checkForInput() {
		// Go back to the menu if the screen is tapped
		if (Gdx.input.isTouched()) {
			AssetManager.getInstance().getSound("button-click").play();
			gameInstance.setScreen(new MenuScreen(gameInstance));
			return;
		}
	}

	/*
	 * The following methods are unused, but are required by the Screen
	 * interface.
	 */

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

}
