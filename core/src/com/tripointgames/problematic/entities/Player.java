package com.tripointgames.problematic.entities;

import com.badlogic.gdx.math.Vector2;
import com.tripointgames.problematic.AssetManager;

/**
 * The player is the only user-controlled entity and represents the user.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class Player extends Entity {

	public Player() {
		// Set the texture of the player to playerTexture and the position of the player to (0, 0).
		super(AssetManager.getInstance().getTexture("playerTexture"), Vector2.Zero);
	}


}
