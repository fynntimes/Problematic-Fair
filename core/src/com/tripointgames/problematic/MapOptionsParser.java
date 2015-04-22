package com.tripointgames.problematic;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Scans a map for entity locations, animated tiles, and other variables needed
 * by the game.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class MapOptionsParser {

	private TiledMap map;

	// Map options
	private Vector2 playerSpawnLocation;

	public MapOptionsParser(TiledMap map) {
		this.map = map;
	}

	public void parse() {
		/*
		 * ENTITIES LAYER
		 * Load in all entities form the Entities layer. This basically checks
		 * the layer for map objects, and collects data from their properties,
		 * which are set in Tiled (map editor).
		 */

		// Get the layer if it exists, or return if it was not found.
		TiledMapTileLayer entityLayer = (TiledMapTileLayer) this.map
				.getLayers().get("Entities");
		if (entityLayer == null) {
			System.err.println("There is no entity layer in map " + this.map);
			return;
		}

		// Iterate through all objects in the layer
		Iterator<MapObject> mapObjects = entityLayer.getObjects().iterator();
		while (mapObjects.hasNext()) {
			MapObject object = mapObjects.next();

			String type = object.getName();
			if (type.equalsIgnoreCase("player")) {
				// This object represents a player, get location.
				playerSpawnLocation = new Vector2(object.getProperties().get(
						"x", Integer.class), object.getProperties().get("y",
						Integer.class));
			} else {
				// The entity does not exist, print a warning and move on.
				System.err.println("Object found in the entity layer but is not a recognized entity.");
			}
		}

	}

}
