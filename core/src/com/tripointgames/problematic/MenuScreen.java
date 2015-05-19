package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tripointgames.problematic.util.AssetManager;

/**
 * The main menu for the game.
 * 
 * @author Faizaan Datoo
 */
public class MenuScreen implements Screen {

	private Main gameInstance;
	private Stage stage;
	private Table mainContainer;

	/**
	 * @param gameInstance
	 *            Instance of Main for access to variables stored only in the
	 *            Main class.
	 */
	public MenuScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		stage = new Stage();

		initMainContainer();

		// Add buttons to the main container
		addLogo();
		addOptionsButton();
		addPlayButton();
		addHelpButton();

		stage.addActor(mainContainer);
		Gdx.input.setInputProcessor(stage); // Allows the stage to take in input
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();
	}

	/**
	 * Initialize the main table (container) with a background. This table holds
	 * and positions all buttons on the menu.
	 */
	private void initMainContainer() {
		mainContainer = new Table();
		mainContainer.setBackground(AssetManager.getInstance()
				.convertTextureToDrawable("menuBackground"));
		mainContainer.setFillParent(true);
	}

	private void addLogo() {
		Image problematicLogo = new Image(AssetManager.getInstance()
				.convertTextureToDrawable("problematicLogo"));
		mainContainer.add(problematicLogo).colspan(3).align(Align.center).row();
	}

	/**
	 * Add the options button to the main container. The options button is
	 * responsible for taking the user to the options screen.
	 */
	private void addOptionsButton() {
		ImageButton optionsButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("optionsButton"));

		// Listener is fired when the button is pressed, so take the user to the
		// options screen.
		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("To the options screen");
				AssetManager.getInstance().getSound("button-click").play();
			}
		});

		mainContainer.add(optionsButton);
	}

	/**
	 * Add the play button to the main container. The play button is responsible
	 * for taking the user to the level screen, where they choose a level.
	 */
	private void addPlayButton() {
		ImageButton playButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("playButton"));

		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetManager.getInstance().getSound("button-click").play();
				gameInstance.setScreen(new LevelScreen(gameInstance));
			}
		});

		mainContainer.add(playButton);
	}

	/**
	 * Add the help button to the main container. The help button tells the
	 * player how to use the movement controls and how to advance to the next
	 * level.
	 */
	private void addHelpButton() {
		ImageButton helpButton = new ImageButton(AssetManager.getInstance()
				.convertTextureToDrawable("helpButton"));

		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetManager.getInstance().getSound("button-click").play();
				gameInstance.setScreen(new MathScreen(gameInstance));
			}
		});

		mainContainer.add(helpButton);
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
