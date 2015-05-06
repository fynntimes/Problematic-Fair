package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * The level selection screen.
 * 
 * @author Faizaan Datoo, Willie Hawley, and Alex Cevicelow
 */
public class LevelScreen implements Screen {

	/** Determines the amount of pages shown on the level screen. */
	public static final int LEVEL_PACKS = 3;

	/** The size of each level button, in pixels. */
	public static final int BUTTON_SIZE = 150;

	Main gameInstance; // Instance of the main class

	private Skin skin;
	private Stage stage;
	private Table container;

	// Holds the level pack names.
	private String[] packNames = { "Grassy Journey", "Rocky Road", "Sandstorm" };

	public LevelScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		stage = new Stage();

		// Initialize the skin used for each button. A skin is the set of colors
		// and dimensions that is applied to each UI element.
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"),
				new TextureAtlas("skin/uiskin.atlas"));
		// Button style for an unlocked level (red button)
		skin.add("top", skin.newDrawable("default-round", Color.RED),
				Drawable.class);
		// Button style for a locked level (grey button)
		skin.add("locked-level", skin.newDrawable("default-round", Color.GRAY),
				Drawable.class);
		// Filled yellow square (star)
		skin.add("star-filled", skin.newDrawable("white", Color.YELLOW),
				Drawable.class);
		// Filled grey square (unused star)
		skin.add("star-unfilled", skin.newDrawable("white", Color.GRAY),
				Drawable.class);

		// Allow this stage to accept input
		Gdx.input.setInputProcessor(stage);

		// Create a table to store the buttons on.
		container = new Table();
		stage.addActor(container);
		container.setBackground(new TextureRegionDrawable(new TextureRegion(
				AssetManager.getInstance().getTexture("menuBackground"))));
		container.setFillParent(true);

		PagedScrollPane scroll = new PagedScrollPane();
		scroll.setFlingTime(0.1f); // Amount of time that a turn-page gesture
									// (fling) lasts.
		int levelId = 1;
		for (int page = 0; page < LEVEL_PACKS; page++) {
			Table levels = new Table().pad(50);
			levels.defaults().pad(20, 40, 20, 40);
			levels.add(new Label(packNames[page], skin)).colspan(1).row();
			for (int y = 0; y < 2; y++) {
				levels.row();
				for (int x = 0; x < 4; x++) {
					levels.add(getLevelButton(levelId++)).expand().fill();
				}
			}
			scroll.addPage(levels);
		}
		scroll.setPageSpacing(200); // Space each page apart by 200 pixels
		container.add(scroll).expand().fill();
	}

	@Override
	public void render(float delta) {
		// Update and render the stage
		stage.act(Gdx.graphics.getDeltaTime());
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
		stage.dispose();
		skin.dispose();
	}

	/**
	 * Creates a button to represent the level
	 * 
	 * @param level
	 * @return The button to use for the level
	 */
	public Button getLevelButton(int level) {
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

		boolean unlocked = data == null ? false : data.unlocked;
		int stars = data == null ? 0 : data.starsEarned;

		if (unlocked) { // Show the button as unlocked
			// Stack the button image and label on top of the button
			button.stack(new Image(skin.getDrawable("top")), label)
					.width(BUTTON_SIZE).height(BUTTON_SIZE).expand().fill();
		} else { // Show the button as disabled
			button.stack(new Image(skin.getDrawable("locked-level")), label)
					.width(BUTTON_SIZE).height(BUTTON_SIZE).expand().fill();
		}

		// Set the amount of stars shown under the level's icon.
		Table starTable = new Table();
		starTable.defaults().pad(5);
		if (stars > 0) {
			for (int star = 0; star < 3; star++) {
				if (stars > star) {
					starTable.add(new Image(skin.getDrawable("star-filled")))
							.width(30).height(30);
				} else {
					starTable.add(new Image(skin.getDrawable("star-unfilled")))
							.width(30).height(30);
				}
			}
		}

		button.row();
		button.add(starTable).height(30);

		button.setName(Integer.toString(level));
		button.addListener(levelClickListener);
		return button;
	}

	/**
	 * Handle a button click/tap by going to the level that was tapped.
	 */
	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			int levelId = Integer.parseInt(event.getListenerActor().getName());
			if (gameInstance.levelManager.isLevel(levelId)) {
				gameInstance.setScreen(new GameScreen(gameInstance.levelManager
						.getLevel(levelId)));
			}
		}
	};

}
