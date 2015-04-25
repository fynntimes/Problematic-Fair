package com.tripointgames.problematic.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tripointgames.problematic.entities.Player;

/**
 * GameWorld contains the current map and entity positions.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameWorld {

	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private SpriteBatch mapBatch;
	private MapOptionsParser parser;

	private Player player;

	public GameWorld(TiledMap map) {
		// Initialize a player and set the map.
		this.player = new Player();
		setMap(map);
	}

	/**
	 * Set the current map (level). This will initialize/reinitialize the map
	 * renderer, map batch, options parser, and will set the player's position
	 * to the beginning of the map to match the new map's preferences.
	 * 
	 * @param map
	 *            The map to switch to.
	 */
	public void setMap(TiledMap map) {
		this.map = map;

		// TiledMapRenderer is responsible for rendering a tiled map.
		// It is included in LibGDX.
		this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
		this.mapBatch = (SpriteBatch) this.mapRenderer.getBatch();

		// Use the MapOptionsParser to get data about the map.
		this.parser = new MapOptionsParser(map);
		this.parser.parse();

		// Apply that data
		this.player.setPosition(parser.getPlayerSpawnLocation());
	}

	/**
	 * Update animations and player input/movement.
	 */
	public void update() {
		// Update player horizontal collision
		if (player.collision(getTilePositions(player.getPosition(), 3, 0)))
			player.setVelocityX(0);
		// Update player vertical collision
		if (player.collision(getTilePositions(player.getPosition(), 0, 3)))
			player.setVelocityY(0);
		// Update player
		player.update();
	}
	
	/**
	 * Draw the map and entities.
	 */
	public void render() {
		this.mapRenderer.render();
		player.render(this.mapBatch);
	}

	/**
	 * Dispose of all resources.
	 */
	public void dispose() {
		this.mapRenderer.dispose();
		this.map.dispose();
	}

	/**
	 * Get the tile positions within a certain range of a position.
	 * 
	 * @param position
	 *            The position to start at (usually the entity being collided)
	 * @param horizontalRadius
	 *            The radius that the method will scan horizontally.
	 * @param verticalRange
	 *            The range, from the entity's Y up, that the method will scan
	 *            vertically.
	 * @return An Array of all the positions (Vector2) of the tiles that were
	 *         found.
	 */
	private Array<Vector2> getTilePositions(Vector2 position,
			int horizontalRadius, int verticalRange) {
		// Get the collision layer
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(
				"Collision");
		Array<Vector2> tiles = new Array<Vector2>();

		// Loop through all the tiles from the player's position to the
		// verticalRange.
		for (int y = Math.round(position.y); y < position.y + verticalRange; y++) {
			// Loop through all the tiles "horizontalRadius" behind and
			// "horizontalRadius" in front of the player.
			for (int x = Math.round(position.x - horizontalRadius); x < position.x
					+ horizontalRadius; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null)
					tiles.add(new Vector2(x, y));
			}
		}

		return tiles;
	}

}
