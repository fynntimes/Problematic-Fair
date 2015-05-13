package com.tripointgames.problematic.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.tripointgames.problematic.AssetManager;

/**
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class EntityPlayer extends EntityBase {

	public EntityPlayer() {
		super(AssetManager.getInstance().getTexture("koala"), 18, 26);
	}

	@Override
	public void handleInput() {
		// Check for keyboard input
		// TODO Add button input
		if ((Gdx.input.isKeyPressed(Keys.SPACE)) && onGround) {
			velocity.y += jumpVelocity;
			state = EntityState.Jumping;
			onGround = false;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			velocity.x = -maxVelocity;
			if (onGround)
				state = EntityState.Walking;
			facingRight = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Keys.D)) {
			velocity.x = maxVelocity;
			if (onGround)
				state = EntityState.Walking;
			facingRight = true;
		}
	}

}
