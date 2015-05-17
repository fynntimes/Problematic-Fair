package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tripointgames.problematic.entity.EntityPlayer;
import com.tripointgames.problematic.level.Level;

/**
 * The actual GameScreen, where all the gameplay is handled.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class GameScreen implements Screen {

	// Variables
	public static final float UNIT_SCALE = 1 / 70f; // 1 unit is 16 pixels (i.e.
													// the tile size)
	public static final float GRAVITY = -1.5f; // Y velocity is decreased by
												// this value every frame

	private Main gameInstance;
	
	private EntityPlayer player;
	private OrthographicCamera camera;
	private Level level;
	private GameGUI gui;
	
	private SpriteBatch fontBatch = new SpriteBatch();
	private BitmapFont font = new BitmapFont();

	public GameScreen(Level level, Main gameInstance) {
		this.level = level;
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		gui = new GameGUI();
		player = new EntityPlayer(gui);

		// Creates a camera which will show 10x5 units of the world.
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10, 5);
		camera.update();

		level.prepare(camera, player, gameInstance);
	}

	@Override
	public void render(float delta) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		level.update(deltaTime);
		level.render();

		gui.render();
		
		fontBatch.begin();
		font.draw(fontBatch, "X: " + player.position.x + ", Y: " + player.position.y
				+ ", CamX: " + camera.position.x+ ", CamY: " + camera.position.y + ", OnGround? " + player.onGround, 0, 20);
		fontBatch.end();
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
