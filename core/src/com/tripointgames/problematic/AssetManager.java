package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Singleton class that stores all game assets, such as textures, maps, and
 * sounds.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class AssetManager {

	// Private static instance of AssetManager.
	private static AssetManager instance;

	/**
	 * Statically get the instance of the AssetManager.
	 * 
	 * @return The AssetManager instance.
	 */
	public static AssetManager getInstance() {
		if (instance == null)
			instance = new AssetManager();
		return instance;
	}

	// Stores all assets by their keys.
	private ObjectMap<String, Object> assetMap;

	protected AssetManager() { // Internal initialization only.
		assetMap = new ObjectMap<String, Object>();
	}

	/**
	 * Register an asset to the asset map.
	 * 
	 * @param key
	 *            The asset's key.
	 * @param value
	 *            The asset's initialized object.
	 */
	public void registerAsset(String key, Object value) {
		// If the asset is already registered, remove it so it can be replaced.
		if (getAsset(key) != null)
			assetMap.remove(key);
		assetMap.put(key, value);
	}

	/**
	 * Create a Texture object and register it to a texture to the asset map.
	 * 
	 * @param key
	 *            The asset's key.
	 * @param internalPath
	 *            The path of the asset within the assets folder.
	 */
	public void registerTexture(String key, String internalPath) {
		registerAsset(key, new Texture(Gdx.files.internal(internalPath)));
	}

	/**
	 * Create a Sound object and register it to the asset map.
	 * 
	 * @param key
	 *            The asset's key.
	 * @param internalPath
	 *            The path of the asset within the assets folder.
	 */
	public void registerSound(String key, String internalPath) {
		registerAsset(key, Gdx.audio.newSound(Gdx.files.internal(internalPath)));
	}

	/**
	 * Create a TiledMap object and register it to the asset map.
	 * 
	 * @param key
	 *            The asset's key.
	 * @param internalPath
	 *            The path of the asset within the assets folder.
	 */
	public void registerMap(String key, String internalPath) {
		registerAsset(key, new TmxMapLoader().load(internalPath));
	}

	/**
	 * Gets an asset from the map.
	 * 
	 * @param key
	 *            The asset's key.
	 * @return The object you requested, or null if it could not be found.
	 */
	public Object getAsset(String key) {
		return assetMap.get(key);
	}

	/**
	 * Gets an asset from the map and casts it to a Texture object.
	 * 
	 * @param key
	 *            The asset's key.
	 * @return The Texture object you requested, or null if it could not be
	 *         found or is not a Texture object.
	 */
	public Texture getTexture(String key) {
		Object asset = assetMap.get(key);

		// Avoid a class cast exception and null pointer exception by checking
		// if it is an instance of Texture and making sure it is not null.
		if (!(asset instanceof Texture) || asset == null)
			return null;

		return (Texture) asset;
	}

	/**
	 * Gets an asset from the map and casts it to a Sound object.
	 * 
	 * @param key
	 *            The asset's key.
	 * @return The Sound object you requested, or null if it could not be found
	 *         or is not a Sound object.
	 */
	public Sound getSound(String key) {
		Object asset = assetMap.get(key);

		// Avoid a class cast exception and null pointer exception by checking
		// if it is an instance of Sound and making sure it is not null.
		if (!(asset instanceof Sound) || asset == null)
			return null;

		return (Sound) asset;
	}

	/**
	 * Gets an asset from the map and casts it to a TiledMap object.
	 * 
	 * @param key
	 *            The asset's key.
	 * @return The TiledMap object you requested, or null if it could not be
	 *         found or is not a TiledMap object.
	 */
	public TiledMap getMap(String key) {
		Object asset = assetMap.get(key);

		// Avoid a class cast exception and null pointer exception by checking
		// if it is an instance of TiledMap and making sure it is not null.
		if (!(asset instanceof TiledMap) || asset == null)
			return null;

		return (TiledMap) asset;
	}

}