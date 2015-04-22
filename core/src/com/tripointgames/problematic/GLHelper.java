package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Convenience methods for accessing OpenGL.
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GLHelper {
	
	/**
	 * Clear the screen to black. Call this every frame.
	 */
	public static void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 0); // Set the clear color to 0,0,0 - black
		// Clear both color- and depth-related pixels.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}

}
