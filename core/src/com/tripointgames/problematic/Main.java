package com.tripointgames.problematic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * First Problematic class that is loaded by LibGDX, serves as the main class.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 *
 */
public class Main extends Game {

	public LevelManager levelManager;

	@Override
	public void create() {
		AssetManager.getInstance().registerTexture("koala",
				"textures/koalio.png");
		AssetManager.getInstance().registerTexture("problematicLogo",
				"textures/Problematic-2.png");
		AssetManager.getInstance().registerTexture("menuBackground",
				"textures/bg_castle.png");
		AssetManager.getInstance().registerTexture("playButton",
				"textures/play.png");
		AssetManager.getInstance().registerTexture("optionsButton",
				"textures/options.png");
		AssetManager.getInstance().registerTexture("helpButton",
				"textures/help.png");

		levelManager = new LevelManager();
		levelManager.loadLevels();

		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		// Clear the screen to a blue color
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render the current screen
		super.render();
	}

	@Override
	public void dispose() {
		// Dispose of all assets on exit
		AssetManager.getInstance().disposeAll();
	}

}
