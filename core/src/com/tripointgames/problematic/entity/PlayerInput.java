package com.tripointgames.problematic.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.tripointgames.problematic.GameGUI;
import com.tripointgames.problematic.Main;

/**
 * Handles input from the game GUI movement buttons, and responds by moving the
 * player.
 * 
 * @author Faizaan Datoo
 *
 */
public class PlayerInput implements InputProcessor {

	private GameGUI gui;
	private EntityPlayer player;

	private boolean left = false;
	private boolean right = false;
	private boolean jump = false;

	public PlayerInput(GameGUI gui, EntityPlayer player) {
		this.gui = gui;
		this.player = player;

		// Allow this class to take in input
		Gdx.input.setInputProcessor(this);
	}

	/**
	 * Called every frame. This updates the player movement.
	 */
	public void checkInput() {
		if (left) player.left();
		if (right) player.right();
		if (jump) player.jump();
	}

	/**
	 * Checks if a certain button is touched.
	 */
	private boolean isButtonTouched(float mouseX, float mouseY, float startX,
			float startY) {
		float endX = startX + GameGUI.GRID_WIDTH;
		float endY = startY + GameGUI.GRID_HEIGHT;
		// Subtract the grid height to fix an issue with position offset.
		mouseY = mouseY - GameGUI.GRID_HEIGHT;

		if (Gdx.input.isTouched()) {
			// If the mouse X and Y is within the bounds
			if ((mouseX >= startX && mouseX <= endX)
					&& (mouseY >= startY && mouseY <= endY)) {
				// Return true
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the coordinates passed are within the jump button.
	 * 
	 * @param x
	 *            The X coordinate of the tap.
	 * @param y
	 *            The Y coordinate of the tap.
	 * @return True if the coordinates are within the jump button's area.
	 */
	private boolean isJumpButton(float x, float y) {
		y = y - 128;

		float startX = gui.jumpButtonLeftX;
		float startY = gui.jumpButtonY;
		float endX = gui.jumpButtonLeftX + GameGUI.GRID_WIDTH;
		float endY = startY + GameGUI.GRID_HEIGHT;

		// Check the left button
		if ((x >= startX && x <= endX) && (y >= startY && y <= endY)) {
			return true;
		}

		// Check the right button
		startX = gui.jumpButtonRightX;
		endX = gui.jumpButtonRightX + GameGUI.GRID_WIDTH;
		if ((x >= startX && x <= endX) && (y >= startY && y <= endY)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.A) {
			left = true;
		}
		if (keycode == Keys.D) {
			right = true;
		}

		if (keycode == Keys.SPACE) {
			jump = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.A) {
			left = false;
		}
		if (keycode == Keys.D) {
			right = false;
		}

		if (keycode == Keys.SPACE) {
			jump = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Check which button was pressed.

		if (isButtonTouched(screenX, screenY, gui.leftButtonX, gui.leftButtonY)) {
			left = true;
		}
		if (isButtonTouched(screenX, screenY, gui.rightButtonX, gui.rightButtonY)) {
			right = true;
		}

		if (isButtonTouched(screenX, screenY, gui.jumpButtonLeftX, gui.jumpButtonY)
				|| isButtonTouched(screenX, screenY, gui.jumpButtonRightX,
						gui.jumpButtonY)) {
			jump = true;
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// This if statement only resets movement if the jump button is not
		// pressed.
		// That way, the player can still jump even if it is idle.
		if (!isJumpButton(screenX, screenY)) {
			if (left) left = false;
			if (right) right = false;
		}

		if (jump) jump = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}