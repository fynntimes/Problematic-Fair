package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameScreen implements Screen {

	// Variables
	public static final float UNIT_SCALE = 1 / 70f; // 1 unit is 16 pixels (i.e.
													// the tile size)
	public static final float GRAVITY = -1.5f; // Y velocity is decreased by
												// this value every frame

	private EntityPlayer player;
	private OrthographicCamera camera;
	private Level level;

	@Override
	public void show() {
		// Create the player entity.
		player = new EntityPlayer();

		// Create a camera which will show 10x5 units of the world.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 5);
		camera.update();

		// Create the level
		level = new Level(AssetManager.getInstance().getMap("level1"), camera,
				player);
	}

	@Override
	public void render(float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		level.update(deltaTime);
		level.render();
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
		level.dispose();
	}

}
