package com.tripointgames.problematic.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.tripointgames.problematic.GameScreen;

/**
 * Base class for all entities. Man
 * 
 * @author Faizaan Datoo
 */
public abstract class EntityBase {

	protected TextureAtlas textureAtlas;
	protected float width, height;
	protected Animation standing, walking, jumping;

	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2();
	protected float maxVelocity = 5f;
	protected float jumpVelocity = 20f;
	protected float movementDamper = 0.87f;

	protected EntityState state = EntityState.Standing;
	protected float stateTime = 0; // Stores animation frame
	protected boolean facingRight = true; // Flips the texture

	public boolean onGround = true; // False when entity is jumping
	public boolean alive = true; // False when entity dies

	/*
	 * A Pool is a group of objects (of the same type) that can be reused. This
	 * avoids object reallocation, which can take a huge toll on performance.
	 */
	private Pool<Rectangle> rectanglePool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	protected EntityBase(String textureAtlasLocation) {
		createAnimations(textureAtlasLocation);
	}

	/**
	 * Create the animations used by this entity. May be overridden if this
	 * entity does not have all 3 animations (jumping, walking, and standing).
	 * 
	 * @param spritesheet
	 * @param spriteWidth
	 * @param spriteHeight
	 */
	protected void createAnimations(String textureAtlasLocation) {
		// Load the texture atlas from the texture atlas file
		this.textureAtlas = new TextureAtlas(
				Gdx.files.internal(textureAtlasLocation));

		// Initialize the animations for this entity
		standing = new Animation(0, textureAtlas.findRegion("standing"));
		jumping = new Animation(0, textureAtlas.findRegion("jumping"));
		walking = new Animation(0.15f, textureAtlas.findRegion("walking1"),
				textureAtlas.findRegion("walking2"),
				textureAtlas.findRegion("walking3"));
		walking.setPlayMode(PlayMode.LOOP_PINGPONG);

		// Get the width of this entity
		this.width = GameScreen.UNIT_SCALE
				* textureAtlas.findRegion("standing").getRegionWidth();
		this.height = GameScreen.UNIT_SCALE
				* textureAtlas.findRegion("standing").getRegionHeight();

	}

	public void update(float deltaTime, TiledMap currentMap) {
		stateTime += deltaTime;

		handleInput();

		// Apply gravity
		velocity.add(0, GameScreen.GRAVITY);

		// Clamp the X velocity to the maximum
		if (Math.abs(velocity.x) > maxVelocity) {
			velocity.x = Math.signum(velocity.x) * maxVelocity;
		}

		// Clamp the X velocity to 0 if it's < 1, and set the state to standing
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (onGround) state = EntityState.Standing;
		}

		// Multiply by delta to determine how far to travel in this frame.
		velocity.scl(deltaTime);

		checkCollisionDetection(currentMap);

		// Add the velocity to the position, and un-multiply the velocity to
		// undo the multiplication done before collision detection.
		position.add(velocity);
		velocity.scl(1 / deltaTime);

		// Decrease velocity by the damper to gradually decrease the velocity
		// (since velocity is added every frame)
		velocity.x *= movementDamper;

	}

	// This can be handled by subclasses.
	protected void handleInput() {
	}

	private void checkCollisionDetection(TiledMap currentMap) {
		// Create a bounding box surrounding this entity
		Rectangle entityBoundingBox = getBoundingBox();

		// Stores the radius of tiles to check around the entity.
		int startX, startY, endX, endY;

		// Calculate which tiles to check on the x-axis.
		if (velocity.x > 0) startX = endX = (int) (position.x + width + velocity.x);
		else startX = endX = (int) (position.x + velocity.x);

		// Calculate which tiles to check on the y-axis.
		startY = (int) position.y;
		endY = (int) (position.y + height);

		Array<Rectangle> tiles = getTiles(startX, startY, endX, endY, currentMap);

		handleHorizontalCollision(entityBoundingBox, tiles);
		entityBoundingBox.x = position.x;

		// If velocity is positive, check tiles above entity. Otherwise check
		// the tiles below.
		if (velocity.y > 0) startY = endY = (int) (position.y + height + velocity.y);
		else startY = endY = (int) (position.y + velocity.y);

		startX = (int) position.x;
		endX = (int) (position.x + width);

		tiles = getTiles(startX, startY, endX, endY, currentMap);

		handleVerticalCollision(entityBoundingBox, tiles);

		// Done with the rectangle, free it.
		rectanglePool.free(entityBoundingBox);
	}

	private Rectangle getBoundingBox() {
		// Create a bounding box around the entity
		Rectangle entityBoundingBox = rectanglePool.obtain();
		entityBoundingBox.set(position.x, position.y, width, height);
		return entityBoundingBox;
	}

	private void handleHorizontalCollision(Rectangle boundingBox,
			Array<Rectangle> tiles) {
		// Grow the bounding box by the velocity of the entity to check for
		// forward collisions.
		boundingBox.x += velocity.x;
		for (Rectangle tile : tiles) {
			// Stop the entity upon horizontal collision with a tile.
			if (boundingBox.overlaps(tile)) {
				velocity.x = 0;
			}
		}
	}

	private void handleVerticalCollision(Rectangle boundingBox, Array<Rectangle> tiles) {
		boundingBox.y += velocity.y;
		for (Rectangle tile : tiles) {
			if (boundingBox.overlaps(tile)) {
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
	}

	// Gets all tiles from (startX, startY) to (endX, endY) in the "walls"
	// layer of the passed in map.
	private Array<Rectangle> getTiles(int startX, int startY, int endX, int endY,
			TiledMap currentMap) {
		Array<Rectangle> tiles = new Array<Rectangle>();
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers().get(
				"walls");
		rectanglePool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectanglePool.obtain();
					// Each rectangle is 1x1 due to the unit scale being the
					// tile size. However, if the tile is a quarter one,
					// the height will be smaller.

					float tileHeight = 1f;
					if (cell.getTile().getProperties().containsKey("quarterTile"))
						tileHeight = 0.25f;
					rect.set(x, y, 1, tileHeight);
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
		this.textureAtlas.dispose();
	}

}
