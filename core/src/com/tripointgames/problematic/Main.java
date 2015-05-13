package com.tripointgames.problematic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.tripointgames.problematic.level.LevelManager;

/**
 * The main game class. This will load all the assets needed by the game, as
 * well as initialize the background music and the LevelManager. It will then
 * switch to the Menu screen.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 *
 */
public class Main extends Game {

	public LevelManager levelManager;

	public boolean levelEditor = false;

	@Override
	public void create() {
		loadAssets();

		// Load all levels into the game
		levelManager = new LevelManager();
		levelManager.loadLevels();

		this.setScreen(new MenuScreen(this));
	}

	private void loadAssets() {
		// Load the game textures
		AssetManager.getInstance().registerTexture("koala",
				"textures/koalio.png");
		// Load the UI textures
		AssetManager.getInstance().registerTexture("grassyJourney-logo",
				"textures/Grassy-Journey.png");
		AssetManager.getInstance().registerTexture("rockyRoad-logo",
				"textures/Rocky-Road.png");
		AssetManager.getInstance().registerTexture("sandstorm-logo",
				"textures/Sandstorm.png");
		AssetManager.getInstance().registerTexture("menuBackground",
				"textures/bg_castle.png");
		AssetManager.getInstance().registerTexture("playButton",
				"textures/play.png");
		AssetManager.getInstance().registerTexture("optionsButton",
				"textures/options.png");
		AssetManager.getInstance().registerTexture("helpButton",
				"textures/help.png");
		AssetManager.getInstance().registerTexture(
				"problematicLogo",
				levelEditor ? "textures/problematic-leveleditor.png"
						: "textures/Problematic-2.png");
	}

	@Override
	public void render() {
		// Clear the screen to a blue color
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// The superclass' render method renders the current screen
		super.render();
	}

	@Override
	public void dispose() {
		// Dispose of all assets on exit
		AssetManager.getInstance().disposeAll();
	}

}
