package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * The main menu for the game.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class MenuScreen implements Screen {

	private Main mainInstance;
	private Stage stage;

	public MenuScreen(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void show() {
		stage = new Stage();

		// Initialize the table with a background
		Table table = new Table();
		table.setBackground(assetToTextureRegion("menuBackground"));
		table.setFillParent(true);

		// Add the Problematic logo
		Image problematicLogo = new Image(
				assetToTextureRegion("problematicLogo"));
		table.add(problematicLogo).colspan(3).align(Align.center).row();

		ImageButton optionsButton = new ImageButton(
				assetToTextureRegion("optionsButton"));

		// Add a listener to the options button, then add it.
		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("To the options screen");
			}
		});

		table.add(optionsButton);

		ImageButton playButton = new ImageButton(
				assetToTextureRegion("playButton"));

		// Add a listener to the play button, then add it.
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				mainInstance.setScreen(new LevelScreen(mainInstance));
			}
		});

		table.add(playButton);

		ImageButton helpButton = new ImageButton(
				assetToTextureRegion("helpButton"));

		// Add a listener to the help button, then add it.
		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("To the help screen");
			}
		});

		table.add(helpButton);

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage); // Allows the stage to take in input
	}

	private TextureRegionDrawable assetToTextureRegion(String assetKey) {
		return new TextureRegionDrawable(new TextureRegion(AssetManager
				.getInstance().getTexture(assetKey)));
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// Unused
	}

	@Override
	public void pause() {
		// Unused
	}

	@Override
	public void resume() {
		// Unused
	}

	@Override
	public void hide() {
		// Unused
	}

	@Override
	public void dispose() {

	}

}
