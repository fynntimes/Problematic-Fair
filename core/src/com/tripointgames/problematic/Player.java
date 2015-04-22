package com.tripointgames.problematic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * The player is a user-controlled entity. The entire game depends on the values
 * stored in the Player class.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class Player {

	private Texture texture;
	private Vector2 position;
	private boolean facingLeft = true;

	public Player(Texture texture, Vector2 position) {
		this.texture = texture;
		this.position = position;
	}

	/**
	 * Move the player entity. This method will also set the player's facing,
	 * which will determine how it is rendered.
	 * 
	 * @param vx
	 *            The amount to increase the current X position by.
	 * @param vy
	 *            The amount to increase the current Y position by.
	 */
	public void move(float vx, float vy) {
		if (vx < 0) {
			facingLeft = true;
		} else {
			facingLeft = false;
		}
		position.x += vx;
		position.y += vy;
	}

	public void render(SpriteBatch batch) {
		// To avoid errors, do not attempt to draw if the batch is not enabled.
		if (!batch.isDrawing())
			return;

		if (facingLeft) {
			// If the player is facing left, do not flip the texture because the texture is left by default.
			batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight());
		} else {
			// If the player is facing right, flip the texture.
			batch.draw(texture, position.x, position.y, -texture.getWidth(), texture.getHeight());
		}
	}

}
