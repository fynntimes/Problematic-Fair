package com.tripointgames.problematic.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tripointgames.problematic.Main;

/**
 * An entity is an object that can move around and has a texture. Gravity and
 * collision physics are applied to it.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public abstract class Entity {

	private Texture texture;
	private Vector2 position;
	private Vector2 velocity = Vector2.Zero;
	private boolean facingLeft = true;

	// Blank rectangles that are used in collide().
	private Rectangle collisionRectangle = new Rectangle(),
			tileRectangle = new Rectangle();

	public Entity(Texture texture, Vector2 position) {
		this.texture = texture;
		this.position = position;
	}

	/**
	 * Checks if there is a collision for this entity.
	 * 
	 * @param tiles
	 *            The positions of the tiles around the entity.
	 * @return true if there is a collision, false otherwise.
	 */
	public boolean collision(Array<Vector2> tiles) {
		// Refresh the rectangle around the entity.
		collisionRectangle.set(position.x, position.y, texture.getWidth(),
				texture.getHeight());
		for (Vector2 tile : tiles) {
			// Set the tile rectangle to match the bounds of the current tile.
			tileRectangle.set(tile.x, tile.y, Main.TILE_SIZE, Main.TILE_SIZE);
			// Handy util from LibGDX that checks if two rectangles intersect,
			// which equals a collision.
			// The third argument is a rectangle that is made at the area of
			// intersection. This is unused.
			return Intersector.intersectRectangles(collisionRectangle,
					tileRectangle, new Rectangle());
		}

		return false;
	}

	public void update() {
		// Add the velocity vector to the position.
		position.add(velocity);

		// Apply damping
		velocity.x *= Main.VELOCITY_DAMPING;

		// Apply gravity
		velocity.y *= Main.GRAVITY;
	}

	/**
	 * Render the entity to the passed in SpriteBatch. The method will check
	 * which way the entity is facing and will flip its texture accordingly.
	 * 
	 * @param batch
	 *            The SprtieBatch to render to.
	 */
	public void render(SpriteBatch batch) {
		// To avoid errors, do not attempt to draw if the batch is not enabled.
		if (!batch.isDrawing())
			return;

		if (facingLeft) {
			// If the entity is facing left, do not flip the texture because the
			// texture is left by default.
			batch.draw(texture, position.x, position.y, texture.getWidth(),
					texture.getHeight());
		} else {
			// If the entity is facing right, flip the texture.
			batch.draw(texture, position.x, position.y, -texture.getWidth(),
					texture.getHeight());
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocityX(float x) {
		if (x < 0)
			facingLeft = true;
		else
			facingLeft = false;
		velocity.x = x;
	}

	public void setVelocityY(float y) {
		velocity.y = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Texture getTexture() {
		return texture;
	}

}
