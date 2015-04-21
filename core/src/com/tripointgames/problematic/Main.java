package com.tripointgames.problematic;

import com.badlogic.gdx.Game;

/**
 * First Problematic class that is loaded by LibGDX, serves as the main class.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 *
 */
public class Main extends Game {

	/**
	 * Called when the game is first loaded. Initialization code goes here.
	 * 
	 * @see com.badlogic.gdx.Game#create()
	 */
	@Override
	public void create() {
		// Initialize textures

		// Initialize sounds

		// Initialize maps
	}

	/**
	 * Render the current screen to the display.
	 * 
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		GLHelper.clearScreen(); // Clear the screen of colors from last frame.
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
	}

}
