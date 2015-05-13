package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tripointgames.problematic.entity.EntityPlayer;
import com.tripointgames.problematic.level.Level;

/**
 * The actual GameScreen, where all the gameplay is handled.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameScreen implements Screen {

	// TODO Input buttons

	// Variables
	public static final float UNIT_SCALE = 1 / 70f; // 1 unit is 16 pixels (i.e.
													// the tile size)
	public static final float GRAVITY = -1.5f; // Y velocity is decreased by
												// this value every frame

	private EntityPlayer player;
	private OrthographicCamera camera;
	private Level level;

	public GameScreen(Level level) {
		this.level = level;
	}

	@Override
	public void show() {
		player = new EntityPlayer();

		// Create a camera which will show 10x5 units of the world.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 5);
		camera.update();

		level.prepare(camera, player);
	}

	@Override
	public void render(float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		level.update(deltaTime);
		level.render();
	}

	@Override
	public void dispose() {
		level.dispose();
	}

	/*
	 * The following methods are unused but are required by the Screen
	 * interface.
	 */

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

}
