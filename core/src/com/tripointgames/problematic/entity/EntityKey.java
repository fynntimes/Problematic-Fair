package com.tripointgames.problematic.entity;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * The key that the player must collect to go to the next level.
 * 
 * @author Faizaan Datoo
 */
public class EntityKey extends EntityBase{

	public EntityKey() {
		super("textures/key.txt");
	}
	
	@Override
	protected void createAnimations(String textureAtlasLocation) {
		super.createAnimations(textureAtlasLocation);
		
		// Override the entity state to show a solid key
		this.jumping = new Animation(0, textureAtlas.findRegion("standing"));
		this.walking = new Animation(0, textureAtlas.findRegion("standing"));
	}
	
}
