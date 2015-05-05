package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameScreen implements Screen {

	// Variables
	public static final float UNIT_SCALE = 1 / 16f; // 1 unit is 16 pixels (i.e.
													// the tile size)
	public static final float GRAVITY = -2.5f; // Y velocity is decreased by
												// this value every frame

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private EntityPlayer player;

	@Override
	public void show() {
		// Loads the level 1 map (temporary code until level selector is done)
		map = AssetManager.getInstance().getMap("level1");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);

		// Create a camera which will show 30x20 units of the world.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		// Create the player which is controlled by the user
		player = new EntityPlayer();
		player.position.set(20, 20);
	}

	@Override
	public void render(float delta) {
		// Get the current delta time (time last frame took to render in millis)
		float deltaTime = Gdx.graphics.getDeltaTime();

		// Update the player
		player.update(deltaTime, map);

		// Make the camera follow the player
		camera.position.x = player.position.x;
		camera.update();

		// Render the tiled map
		renderer.setView(camera);
		renderer.render();

		// Render the player
		player.render(renderer.getBatch());
	}

	@Override
	public void resize(int width, int height) {
		// Unused
	}

	@Override
	public void pause() {
		// Unused
	}

	@Override
	public void resume() {
		// Unused

	}

	@Override
	public void hide() {
		// Unused
	}

	@Override
	public void dispose() {
	}

}
