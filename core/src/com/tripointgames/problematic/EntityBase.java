package com.tripointgames.problematic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Base class for all entities.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public abstract class EntityBase {

	// Variables
	protected Texture spritesheet;
	protected float width, height;
	protected Animation standing, walking, jumping;

	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	protected float maxVelocity = 10f;
	protected float jumpVelocity = 40f;
	protected float movementDamper = 0.87f;

	protected EntityState state = EntityState.Standing;
	protected float stateTime = 0; // Stores animation frame
	protected boolean facingRight = true; // Flips the texture
	protected boolean onGround = true; // True when entity is jumping

	// A Pool is a way of accessing objects without destroying and creating new
	// ones each frame. This saves a lot of time per frame, since it doesn't
	// waste time doing that each frame while refreshing collision detection.
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	public EntityBase(Texture spritesheet, int individualWidth,
			int individualHeight) {
		this.spritesheet = spritesheet;

		TextureRegion[] individualTextures = TextureRegion.split(spritesheet,
				individualWidth, individualHeight)[0];
		standing = new Animation(0, individualTextures[0]);
		jumping = new Animation(0, individualTextures[1]);
		walking = new Animation(0.15f, individualTextures[2],
				individualTextures[3], individualTextures[4]);
		walking.setPlayMode(PlayMode.LOOP_PINGPONG);

		this.width = GameScreen.UNIT_SCALE
				* individualTextures[0].getRegionWidth();
		this.height = GameScreen.UNIT_SCALE
				* individualTextures[0].getRegionHeight();
	}

	public void update(float deltaTime, TiledMap currentMap) {
		stateTime += deltaTime;

		// Handle input
		input();

		// Apply gravity
		velocity.add(0, GameScreen.GRAVITY);

		// Clamp the X velocity to the maximum
		if (Math.abs(velocity.x) > maxVelocity) {
			velocity.x = Math.signum(velocity.x) * maxVelocity;
		}

		// Clamp the X velocity to 0 if it's < 1, and set the state to standing
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (onGround)
				state = EntityState.Standing;
		}

		// Multiply by delta to determine how far to travel in this frame.
		velocity.scl(deltaTime);

		collide(currentMap);

		// Add the velocity to the position, and un-multiply the velocity to
		// undo the multiplication done before collision detection.
		position.add(velocity);
		velocity.scl(1 / deltaTime);

		// Decrease velocity by the damper to gradually decrease the velocity
		// (since velocity is added every frame)
		velocity.x *= movementDamper;

	}

	// This can be handled by subclasses.
	public void input() {
	}

	private void collide(TiledMap currentMap) {
		Rectangle entityBoundingBox = rectPool.obtain();
		entityBoundingBox.set(position.x, position.y, width, height);
		int startX, startY, endX, endY;
		// Set startX and endX to the appropriate boundaries.
		if (velocity.x > 0) {
			startX = endX = (int) (position.x + width + velocity.x);
		} else {
			startX = endX = (int) (position.x + velocity.x);
		}
		startY = (int) position.y;
		endY = (int) (position.y + height);
		// Get the tiles in this range
		Array<Rectangle> tiles = getTiles(startX, startY, endX, endY,
				currentMap);
		// Grow the bounding box by the velocity of the entity to check for
		// forward collisions.
		entityBoundingBox.x += velocity.x;
		for (Rectangle tile : tiles) {
			// Stop the entity upon horizontal collision with a tile.
			if (entityBoundingBox.overlaps(tile)) {
				velocity.x = 0;
				break;
			}
		}
		entityBoundingBox.x = position.x;

		// If the entity is moving up, check tiles above the entity.
		if (velocity.y > 0) {
			startY = endY = (int) (position.y + height + velocity.y);
		} else { // If it is not moving up, check tiles below the entity.
			startY = endY = (int) (position.y + velocity.y);
		}

		// Same thing as above, but with Y values instead.
		startX = (int) position.x;
		endX = (int) (position.x + width);
		tiles = getTiles(startX, startY, endX, endY, currentMap);
		entityBoundingBox.y += velocity.y;
		for (Rectangle tile : tiles) {
			if (entityBoundingBox.overlaps(tile)) {
				// Above collision, set the entity's y to just below the tile
				if (velocity.y > 0) {
					position.y = tile.y - height;
				} else {
					// Below collision, set the entity's y to just above the
					// tile
					position.y = tile.y + tile.height;
					onGround = true;
				}
				// Stop moving on Y
				velocity.y = 0;
				break;
			}
		}
		// Done with the rectangle, free it.
		rectPool.free(entityBoundingBox);
	}

	// Gets all tiles from (startX, startY) to (endX, endY) in the "Collision"
	// layer of the passed in map.
	private Array<Rectangle> getTiles(int startX, int startY, int endX,
			int endY, TiledMap currentMap) {
		Array<Rectangle> tiles = new Array<Rectangle>();
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers()
				.get("walls");
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
		return tiles;
	}

	public void render(Batch batch) {
		// Set the texture based on the entity's state
		TextureRegion frame = null;
		switch (state) {
		case Standing:
			frame = standing.getKeyFrame(stateTime);
			break;
		case Walking:
			frame = walking.getKeyFrame(stateTime);
			break;
		case Jumping:
			frame = jumping.getKeyFrame(stateTime);
			break;
		}

		// Draw the entity. If the entity is facing left, flip its texture.
		batch.begin();
		if (facingRight) {
			batch.draw(frame, position.x, position.y, width, height);
		} else {
			batch.draw(frame, position.x + width, position.y, -width, height);
		}
		batch.end();
	}

	public void dispose() {

	}

}
