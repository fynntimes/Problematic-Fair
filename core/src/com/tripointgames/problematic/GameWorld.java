package com.tripointgames.problematic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * GameWorld contains the current map and entity positions.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameWorld {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private SpriteBatch mapBatch;
	
	private Player player;
	
	public GameWorld(TiledMap map, Player player) {
		this.map = map;
		this.player = player;
		
		// TiledMapRenderer is responsible for rendering a tiled map.
		// It is included in LibGDX.
		this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
		this.mapBatch = (SpriteBatch) this.mapRenderer.getBatch();
	}

	/**
	 * Update animations and player input/movement.
	 */
	public void update() {
		
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
	
}
