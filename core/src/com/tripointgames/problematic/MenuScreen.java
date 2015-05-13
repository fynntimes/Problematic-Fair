package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tripointgames.problematic.level.Level;

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

		// This table holds and positions all buttons on the menu.
		Table table = new Table();
		table.setBackground(AssetManager.getInstance()
				.convertTextureToDrawable("menuBackground"));
		table.setFillParent(true);

		Image problematicLogo = new Image(AssetManager.getInstance()
				.convertTextureToDrawable("problematicLogo"));
		table.add(problematicLogo).colspan(3).align(Align.center).row();

		ImageButton optionsButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("optionsButton"));

		// Listener is fired when the button is pressed.
		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("To the options screen");
			}
		});

		table.add(optionsButton);

		ImageButton playButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("playButton"));

		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (mainInstance.levelEditor) {
					TiledMap map = new TmxMapLoader(
							new AbsoluteFileHandleResolver()).load(System
							.getProperty("user.dir") + "/level.tmx");
					AssetManager.getInstance()
							.registerAsset("levelEditor", map);
					mainInstance.setScreen(new GameScreen(new Level(
							"levelEditor")));
				} else {
					mainInstance.setScreen(new LevelScreen(mainInstance));
				}
			}
		});

		table.add(playButton);

		ImageButton helpButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("helpButton"));

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

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
	}

	/*
	 * The following methods are unused, but the Screen interface requires them.
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

	@Override
	public void dispose() {
	}

}
