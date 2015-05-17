package com.tripointgames.problematic.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.tripointgames.problematic.GameGUI;
import com.tripointgames.problematic.util.AssetManager;

/**
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class EntityPlayer extends EntityBase {

	PlayerInput input;

	public EntityPlayer(GameGUI gui) {
		super(AssetManager.getInstance().getTexture("koala"), 18, 26);
		this.input = new PlayerInput(gui, this);
	}

	@Override
	public void update(float deltaTime, TiledMap currentMap) {
		super.update(deltaTime, currentMap);

		// The player has fallen off the map, kill it.
		if (position.y < -85) alive = false;
	}

	@Override
	protected void handleInput() {
		// Check for keyboard input
		if ((Gdx.input.isKeyPressed(Keys.SPACE)) && onGround) {
			jump();
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			left();
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			right();
		}

		// Check for button input
		this.input.checkInput();
	}

	// These methods are local because they can to be accessed by PlayerInput.

	void left() {
		velocity.x = -maxVelocity;
		if (onGround) state = EntityState.Walking;
		facingRight = false;
	}

	void right() {
		velocity.x = maxVelocity;
		if (onGround) state = EntityState.Walking;
		facingRight = true;
	}

	void jump() {
		if (!onGround) return;
		velocity.y += jumpVelocity;
		state = EntityState.Jumping;
		onGround = false;
	}

}
