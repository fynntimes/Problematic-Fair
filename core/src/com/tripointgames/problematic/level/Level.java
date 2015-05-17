package com.tripointgames.problematic.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.tripointgames.problematic.GameScreen;
import com.tripointgames.problematic.Main;
import com.tripointgames.problematic.MathScreen;
import com.tripointgames.problematic.entity.EntityPlayer;
import com.tripointgames.problematic.util.AssetManager;

/**
 * Stores map and player data for the level.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class Level {

	private Main gameInstance;

	public String levelAssetKey;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private EntityPlayer player;

	public LevelData levelData; // Data about the level
	private Json json; // JSON object for file writing.

	// TODO Make constructor local (after removal of level editor)
	/**
	 * This constructor is to be used only in the LevelManager class. GameScreen
	 * should use the prepare method to pass in a camera and a player object.
	 */
	public Level(String levelAssetKey) {
		this.levelAssetKey = levelAssetKey;
		this.map = AssetManager.getInstance().getMap(levelAssetKey);

		this.levelData = new LevelData();
		this.json = new Json(OutputType.minimal);
		loadData();

		renderer = new OrthogonalTiledMapRenderer(map, GameScreen.UNIT_SCALE);
	}

	/**
	 * This method should be called by the GameScreen class upon load. This sets
	 * up the level so it is ready to be rendered.
	 */
	public void prepare(OrthographicCamera camera, EntityPlayer player,
			Main gameInstance) {
		this.camera = camera;
		this.player = player;
		this.gameInstance = gameInstance;
		// Get the player spawn position from the map
		MapProperties playerProperties = map.getLayers().get("entities").getObjects()
				.get("player").getProperties();
		float playerSpawnX = playerProperties.get("x", Float.class)
				* GameScreen.UNIT_SCALE;
		float playerSpawnY = playerProperties.get("y", Float.class)
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

		// Check if the player is dead
		if (!player.alive) {
			// Switch to the math screen
			AssetManager.getInstance().getSound("player-death").play();
			gameInstance.setScreen(new MathScreen(gameInstance));
			return;
		}

		// Make the camera follow the player
		camera.position.x = player.position.x;
		adjustCamera();
		camera.update();

	}

	public void render() {
		// Render the map
		renderer.setView(camera);
		renderer.render();

		// Render the player
		player.render(renderer.getBatch());
	}

	public void loadData() {
		FileHandle levelHandle = Gdx.files.local("levels/" + levelAssetKey + ".json");
		if (!levelHandle.exists()) save(); // Create the LevelData file.

		this.levelData = json.fromJson(LevelData.class, levelHandle);
	}

	public void save() {
		FileHandle levelHandle = Gdx.files.local("levels/" + levelAssetKey + ".json");
		levelHandle.writeString(json.toJson(levelData), false);
	}

	public void dispose() {
		// Dispose of all resources to free memory
		map.dispose();
		renderer.dispose();
		if (player != null) player.dispose();
	}

	public int getMapWidth() {
		return map.getProperties().get("width", Integer.class);
	}

	private void adjustCamera() {
		// Stop the camera if it goes off the map so the player does not see
		// past the edge.
		if (camera.position.x < 5) {
			camera.position.x = 5;
		}
		if (camera.position.x > getMapWidth()) {
			camera.position.x = getMapWidth();
		}
	}

}