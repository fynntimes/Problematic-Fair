package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Faizaan Datoo
 */
public class OptionsScreen implements Screen {

	Main mainInstance;
	
	Stage stage;
	Skin uiSkin;
	
	public OptionsScreen(Main mainInstance) {
		this.mainInstance = mainInstance;
	}
	
	@Override
	public void show() {
		stage = new Stage();
		uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas("skin/uiskin.atlas"));
		
		Table mainContainer = new Table(uiSkin);
		// TODO Complete this
	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void dispose() {

	}

	/*
	 * Unused methods that are required by the Screen interface.
	 */

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

}
