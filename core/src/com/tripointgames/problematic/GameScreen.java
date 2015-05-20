package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tripointgames.problematic.entity.EntityPlayer;
import com.tripointgames.problematic.level.Level;

/**
 * The actual GameScreen, where all the gameplay is handled.
 * 
 * @author Faizaan Datoo
 */
public class GameScreen implements Screen {

	// Variables
	public static final float UNIT_SCALE = 1 / 70f; // 1 unit is 16 pixels (i.e.
													// the tile size)
	public static final float GRAVITY = -1.25f; // Y velocity is decreased by
												// this value every frame

	private Main gameInstance;

	public EntityPlayer player;
	public OrthographicCamera camera;
	public Level level;
	public GameGUI gui;

	private PauseOverlayScreen pauseScreen;

	public GameScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		gui = new GameGUI();
		player = new EntityPlayer(gui);

		// Creates a camera which will show 15x7 units of the world.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 15, 7);
		camera.update();

		level = gameInstance.levelManager.getCurrentLevel();
		level.prepare(camera, player, gameInstance, this);
	}

	@Override
	public void render(float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		if (!player.input.paused) level.update(deltaTime);
		level.render();

		gui.render();

		if (player.input.paused) {
			pauseGame();
		} else {
			unpauseGame();
		}
	}

	private void pauseGame() {
		if (pauseScreen == null)
			pauseScreen = new PauseOverlayScreen(gameInstance, this);
		pauseScreen.render();
	}

	private void unpauseGame() {
		if (pauseScreen != null) {
			pauseScreen.dispose();
			pauseScreen = null;
		}
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

	@Override
	public void dispose() {
	}

}
