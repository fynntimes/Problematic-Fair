package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tripointgames.problematic.level.LevelData;
import com.tripointgames.problematic.util.AssetManager;

/**
 * The level selection screen.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class LevelScreen implements Screen {

	/** Determines the amount of pages shown on the level screen. */
	public static final int LEVEL_PACKS = 3;
	private String[] packNames = { "grassyJourney-logo", "rockyRoad-logo",
			"sandstorm-logo" };

	/** The size of each level button, in pixels. */
	public static final int BUTTON_SIZE = 125;

	Main gameInstance;

	private Skin skin;
	private Stage stage;
	private Table container;

	public LevelScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());

		initializeSkin();

		// Allow this stage to accept input
		Gdx.input.setInputProcessor(stage);

		// Master table that holds all level buttons and logos
		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		// Table which holds each level pack's levels and is added to the Scroll
		// pane.
		Table levelContainer = new Table();
		levelContainer.setBackground(AssetManager.getInstance()
				.convertTextureToDrawable("menuBackground"));

		// Following code just creates the screens for each level pack and adds
		// them to the level container.
		int levelId = 1;
		for (int page = 0; page < LEVEL_PACKS; page++) {
			Table levels = new Table().pad(50);
			// Sets default padding to 20px top/bottom, 40px left/right
			levels.defaults().pad(20, 40, 20, 40);
			Image packLogo = new Image(AssetManager.getInstance().getTexture(
					packNames[page]));
			levels.add(packLogo).colspan(3).row(); // Logo spans 3 columns
			for (int y = 0; y < 2; y++) {
				levels.row();
				for (int x = 0; x < 3; x++) {
					levels.add(getLevelButton(levelId++)).expand().fill();
				}
			}
			levelContainer.add(levels).row();
		}

		// Add the levels to a scroll pane
		ScrollPane scroll = new ScrollPane(levelContainer, skin);
		scroll.setFadeScrollBars(false); // Make scroll bars always show
		container.add(scroll).expand().fill().row();
	}

	@Override
	public void render(float delta) {
		// Update and render the stage
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	/**
	 * Creates a button to represent the level.
	 * 
	 * @param level
	 *            The level number
	 * @return The button to use for the level
	 */
	private Button getLevelButton(int level) {
		Button button = new Button(skin);
		ButtonStyle style = button.getStyle();
		style.up = style.down = null;

		// Create the label to show the level number
		Label label = new Label(Integer.toString(level), skin);
		label.setFontScale(2f);
		label.setAlignment(Align.center);

		// Get the LevelData for the level
		LevelData data = null;
		if (gameInstance.levelManager.isLevel(level)) {
			data = gameInstance.levelManager.getLevel(level).levelData;
		}

		// False if data is not found (i.e. level hasn't been played yet)
		boolean unlocked = data == null ? false : data.isUnlocked();

		if (unlocked) {
			// Stack the button image and label on top of the button
			button.stack(new Image(skin.getDrawable("top")), label)
					.width(BUTTON_SIZE).height(BUTTON_SIZE).expand().fill();
		} else {
			button.stack(new Image(skin.getDrawable("locked-level")), label)
					.width(BUTTON_SIZE).height(BUTTON_SIZE).expand().fill();
		}

		button.setName(Integer.toString(level));
		button.addListener(levelClickListener);
		return button;
	}

	/**
	 * Initialize the skin. The skin is a LibGDX API that tells UI widgets how
	 * to look.
	 */
	private void initializeSkin() {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas(
				"skin/uiskin.atlas"));
		// Set the unlocked level buttons to be green.
		skin.add("top", skin.newDrawable("default-round", Color.GREEN),
				Drawable.class);
		// Set the locked level buttons to be gray.
		skin.add("locked-level", skin.newDrawable("default-round", Color.GRAY),
				Drawable.class);
	}

	/**
	 * Handle a button click/tap by going to the level that was tapped.
	 */
	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			int levelId = Integer.parseInt(event.getListenerActor().getName());
			if (gameInstance.levelManager.isLevel(levelId)) {
				if (gameInstance.levelManager.getLevel(levelId).levelData
						.isUnlocked()) {
					AssetManager.getInstance().getSound("button-click").play();
					gameInstance.levelManager
							.setCurrentLevel(gameInstance.levelManager
									.getLevel(levelId));
					gameInstance.setScreen(new GameScreen(gameInstance.levelManager
							.getLevel(levelId), gameInstance));
				}
			}
		}
	};

	/*
	 * The following methods are unused, but are required by the Screen
	 * interface.
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
