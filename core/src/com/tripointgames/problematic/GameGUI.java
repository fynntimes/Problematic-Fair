package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The GUI for in-game. Includes movement and pause buttons.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 *
 */
public class GameGUI {

	// The size of each icon
	public static final int GRID_WIDTH = 128;
	public static final int GRID_HEIGHT = 128;

	private SpriteBatch batch;

	// Actual buttons and their positions (for input)
	private TextureRegion leftButton;
	public int leftButtonX, leftButtonY;
	private TextureRegion rightButton;
	public int rightButtonX, rightButtonY;
	private TextureRegion jumpButton;
	public int jumpButtonLeftX;
	public int jumpButtonRightX;
	public int jumpButtonY;

	public GameGUI() {
		batch = new SpriteBatch();

		// Initialize the images by getting them from the spritesheet.

		// Left button
		leftButton = new TextureRegion(new Texture(
				Gdx.files.internal("textures/HudSprites.png")), 0, 0, GRID_WIDTH,
				GRID_HEIGHT);

		// Right button
		rightButton = new TextureRegion(new Texture(
				Gdx.files.internal("textures/HudSprites.png")), GRID_WIDTH + 1, 0,
				GRID_WIDTH, GRID_HEIGHT);

		// Jump button
		jumpButton = new TextureRegion(new Texture(
				Gdx.files.internal("textures/HudSprites.png")), (GRID_WIDTH * 2) + 1,
				0, GRID_WIDTH, GRID_HEIGHT);

		// Tablet jump button
		jumpButton = new TextureRegion(new Texture(
				Gdx.files.internal("textures/HudSprites.png")), 0, GRID_HEIGHT + 1,
				GRID_WIDTH, GRID_HEIGHT);

		// Set the positions for each button
		leftButtonX = 0;
		leftButtonY = Gdx.graphics.getHeight() / 2 - GRID_HEIGHT;
		rightButtonX = Gdx.graphics.getWidth() - GRID_WIDTH;
		rightButtonY = Gdx.graphics.getHeight() / 2 - GRID_HEIGHT;

		jumpButtonLeftX = 0;
		jumpButtonRightX = Gdx.graphics.getWidth() - GRID_WIDTH;
		jumpButtonY = Gdx.graphics.getHeight() - (GRID_HEIGHT * 2);

	}

	/**
	 * Draw the buttons to the screen.
	 */
	public void render() {
		batch.begin();
		batch.enableBlending(); // Enable transparency

		batch.draw(leftButton, leftButtonX, leftButtonY);
		batch.draw(rightButton, rightButtonX, rightButtonY);

		batch.draw(jumpButton, jumpButtonLeftX, 0);
		batch.draw(jumpButton, jumpButtonRightX, 0);

		batch.end();
	}

	/**
	 * Remove objects from memory.
	 */
	public void dispose() {
		batch.dispose();
	}

}