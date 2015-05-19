package com.tripointgames.problematic.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.tripointgames.problematic.GameScreen;
import com.tripointgames.problematic.Main;
import com.tripointgames.problematic.MathScreen;
import com.tripointgames.problematic.entity.EntityKey;
import com.tripointgames.problematic.entity.EntityPlayer;
import com.tripointgames.problematic.util.AssetManager;

/**
 * Stores map and player data for the level.
 * 
 * @author Faizaan Datoo
 */
public class Level {

	private Main gameInstance;
	private GameScreen gameScreen;

	public String levelAssetKey;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private EntityPlayer player;

	public EntityKey key;

	public LevelData levelData; // Data about the level
	private Json json; // JSON object for file writing.

	public float yBottom; // The bottom of the map, for camera positioning

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
			Main gameInstance, GameScreen gameScreen) {
		this.camera = camera;
		this.player = player;
		this.gameInstance = gameInstance;
		this.gameScreen = gameScreen;

		// Get the ybottom from the map
		// TODO This is temporary until all maps have a "bottom" object.
		if (map.getLayers().get("entities").getObjects().get("bottom") == null) this.yBottom = 93;
		else this.yBottom = map.getLayers().get("entities").getObjects()
				.get("bottom").getProperties().get("y", Float.class)
				* GameScreen.UNIT_SCALE;
		this.camera.position.y = this.yBottom;

		// Get the key location from the map.
		this.key = new EntityKey();
		if (map.getLayers().get("entities").getObjects().get("key") == null) this.key.position = Vector2.Zero;
		else {
			MapProperties playerProperties = map.getLayers().get("entities").getObjects()
					.get("key").getProperties();
			float keySpawnX = playerProperties.get("x", Float.class)
					* GameScreen.UNIT_SCALE;
			float keySpawnY = playerProperties.get("y", Float.class)
					* GameScreen.UNIT_SCALE;
			this.key.position.set(keySpawnX, keySpawnY);
		}

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
		// If the game is frozen, don't update or this will cause glitching
		if (delta == 0) return;
		// Update the player
		player.update(delta, this.map);

		// Check if the player is dead
		if (!player.alive) {
			// Switch to the math screen
			AssetManager.getInstance().getSound("player-death").play();
			gameInstance.setScreen(new MathScreen(gameInstance));
			return;
		}

		Rectangle playerRect = player.getBoundingBox();
		Rectangle keyRect = key.getBoundingBox();
		if(playerRect.overlaps(keyRect)) {
			this.gameScreen.dispose();
			this.gameInstance.levelManager.incrementLevel();
			this.gameInstance.setScreen(new GameScreen(gameInstance));
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
		
		// Render the key
		key.render(renderer.getBatch());
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
		if (camera.position.x < 8) {
			camera.position.x = 8;
		}

		if (camera.position.x > getMapWidth() - 8) {
			camera.position.x = getMapWidth() - 8;
		}

	}

}