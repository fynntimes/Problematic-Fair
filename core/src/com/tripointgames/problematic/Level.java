package com.tripointgames.problematic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Stores map and player data for the level.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class Level {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private EntityPlayer player;

	public Level(TiledMap map, OrthographicCamera camera, EntityPlayer player) {
		this.map = map;
		this.camera = camera;
		this.player = player;

		renderer = new OrthogonalTiledMapRenderer(map, GameScreen.UNIT_SCALE);

		// Get the player spawn position from the map
		float playerSpawnX = map.getLayers().get("entities").getObjects()
				.get("player").getProperties().get("x", Float.class)
				* GameScreen.UNIT_SCALE;
		float playerSpawnY = map.getLayers().get("entities").getObjects()
				.get("player").getProperties().get("y", Float.class)
				* GameScreen.UNIT_SCALE;

		// Set player position to the spawn point
		player.position.set(playerSpawnX, playerSpawnY);
		camera.position.x = player.position.x;
		camera.position.y = player.position.y + 0.5f;
		camera.update();
	}

	public void update(float delta) {
		// Update the player
		player.update(delta, this.map);

		// Make the camera follow the player
		camera.position.x = player.position.x;
		camera.update();
	}

	public void render() {
		// Render the map
		renderer.setView(camera);
		renderer.render();

		// Render the player
		player.render(renderer.getBatch());
	}

	public void dispose() {
		// Free memory
		map.dispose();
		renderer.dispose();
		player.dispose();
	}

}
