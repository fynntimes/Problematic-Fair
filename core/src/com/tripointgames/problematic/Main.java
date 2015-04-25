package com.tripointgames.problematic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.tripointgames.problematic.screens.GameScreen;

/**
 * First Problematic class that is loaded by LibGDX, serves as the main class.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 *
 */
public class Main extends Game {

	/**
	 * The size of the tiles, in pixels.
	 */
	public static final int TILE_SIZE = 32;

	/**
	 * The amount of gravity. The entity Y is multiplied by this to bring them down.
	 */
	public static final float GRAVITY = -0.1f;

	/**
	 * The velocity is gradually decreased by this value, in order to prevent
	 * the player from abruptly stopping.
	 */
	public static final float VELOCITY_DAMPING = 0.008f;

	/**
	 * Called when the game is first loaded. Initialization code goes here.
	 * 
	 * @see com.badlogic.gdx.Game#create()
	 */
	@Override
	public void create() {
		// Initialize textures
		AssetManager.getInstance().registerTexture("playerTexture",
				"textures/player_left.png");

		// Initialize sounds

		// Initialize maps
		AssetManager.getInstance().registerMap("testLevel",
				"maps/level0.tmx");

		super.setScreen(new GameScreen()); // Set the screen to MenuScreen
	}

	/**
	 * Render the current screen to the display.
	 * 
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		// Clear the screen of colors from last frame.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render(); // Render the current screen.
	}

	/**
	 * Dispose of all resources and screens.
	 * 
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose(); // Dispose of the current screen.
		AssetManager.getInstance().disposeAll(); // Dispose of all assets
	}

}
