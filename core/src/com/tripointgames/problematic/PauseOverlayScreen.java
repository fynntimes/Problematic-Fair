package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tripointgames.problematic.util.AssetManager;

/**
 * The pause screen will be rendered on top of the game screen, which is why it
 * is called an overlay screen. It includes a Resume and Main Menu button.
 * 
 * @author Faizaan Datoo
 */
public class PauseOverlayScreen {

	Main gameInstance;

	Stage stage;
	Skin skin;
	Table mainContainer;

	// Params are final because of the inner classes
	public PauseOverlayScreen(final Main gameInstance, final GameScreen screen) {
		this.gameInstance = gameInstance;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		initUISkin();

		// Lay out the UI
		mainContainer = new Table(skin);
		mainContainer.setFillParent(true); // Fill the whole screen
		stage.addActor(mainContainer);

		TextButton backToGameButton = new TextButton("Back to Game", skin);

		backToGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetManager.getInstance().getSound("button-click").play();
				screen.player.input.paused = false;
			}
		});

		mainContainer.add(backToGameButton).padBottom(20).row();

		TextButton toMainMenuButton = new TextButton("Main Menu", skin);

		toMainMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				AssetManager.getInstance().getSound("button-click").play();
				dispose();
				screen.dispose();
				gameInstance.setScreen(new MenuScreen(gameInstance));
			}
		});

		mainContainer.add(toMainMenuButton);
	}

	public void render() {
		// Draw the UI
		stage.act();
		stage.draw();
	}

	public void dispose() {
		// Destroy all the buttons
		stage.dispose();
	}

	/**
	 * Initialize the UI skin, which determines the colors and fonts for all UI
	 * widgets (actors).
	 */
	private void initUISkin() {
		skin = new Skin();

		// Load fonts into skin
		skin.add("regularFont",
				new BitmapFont(Gdx.files.internal("skin/fonts/chalkboard-font.fnt")));
		skin.add(
				"largeFont",
				new BitmapFont(Gdx.files
						.internal("skin/fonts/chalkboard-font-large.fnt")));

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", 1.0f, 1.0f, 1.0f, 0.5f); // Slightly
																				// translucent
																				// buttons
		textButtonStyle.font = skin.getFont("regularFont");
		skin.add("default", textButtonStyle);
	}

}
