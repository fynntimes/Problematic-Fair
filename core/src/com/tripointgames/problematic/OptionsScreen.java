package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tripointgames.problematic.util.AssetManager;
import com.tripointgames.problematic.util.Difficulty;
import com.tripointgames.problematic.util.PreferencesManager;

/**
 * Allows the user to determine how hard the math problems should be, and what
 * types of problems to show.
 * 
 * @author Faizaan Datoo
 */
public class OptionsScreen implements Screen {

	Main gameInstance; // Instance of the main class

	Stage stage;
	Skin uiSkin;

	public OptionsScreen(Main mainInstance) {
		this.gameInstance = mainInstance;
	}

	@Override
	public void show() {
		// Create a stage and allow it to accept input
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// Load the skin from the skin files and atlas.
		uiSkin = new Skin(Gdx.files.internal("skin/uiskin.json"), new TextureAtlas(
				"skin/uiskin.atlas"));

		// Create the main container and set it to fill the whole screen, and
		// set its background to the menuBackground.
		Table mainContainer = new Table(uiSkin);
		mainContainer.setFillParent(true);
		mainContainer.setBackground(AssetManager.getInstance()
				.convertTextureToDrawable("menuBackground"));

		// Draw the logo in the center of the 3 columns.
		Image optionsLogo = new Image(AssetManager.getInstance()
				.convertTextureToDrawable("options-logo"));
		mainContainer.add(optionsLogo).colspan(3).row();

		// Label for the slider
		Label difficultyLabel = new Label("Difficulty: ", uiSkin);
		mainContainer.add(difficultyLabel);

		// Difficulty slider, allows the user to determine how hard the game is
		final Slider difficultySlider = new Slider(0, 4, 1, false, uiSkin);

		// Set the difficulty value to what the difficulty is in the game.
		Difficulty difficulty = Difficulty.valueOf(PreferencesManager.getInstance()
				.getString("difficulty"));
		difficultySlider.setValue(difficulty.getId());

		// Update the difficulty if this slider is changed
		difficultySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int difficulty = (int) difficultySlider.getValue();
				PreferencesManager.getInstance().set("difficulty",
						Difficulty.values()[difficulty]);
			}
		});

		mainContainer.add(difficultySlider).row();

		// Checkbox to enable addition
		final CheckBox enableAddition = new CheckBox(" Enable Addition", uiSkin);

		// Set the checkbox to checked if addition is enabled in preferences
		enableAddition.setChecked(PreferencesManager.getInstance().getBoolean(
				"showAdditionProblems"));

		// Update the preferences if the checkbox is checked or unchecked.
		enableAddition.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PreferencesManager.getInstance().set("showAdditionProblems",
						enableAddition.isChecked());
			}
		});

		mainContainer.add(enableAddition);

		// Checkbox to enable subtraction
		final CheckBox enableSubtraction = new CheckBox(" Enable Subtraction", uiSkin);

		// Set the checkbox to checked if subtraction is enabled in preferences
		enableSubtraction.setChecked(PreferencesManager.getInstance().getBoolean(
				"showSubtractionProblems"));

		// Update the preferences if the checkbox is checked or unchecked.
		enableSubtraction.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PreferencesManager.getInstance().set("showSubtractionProblems",
						enableAddition.isChecked());
			}
		});

		mainContainer.add(enableSubtraction);

		// Checkbox to enable multiplication
		final CheckBox enableMultiplication = new CheckBox(" Enable Multiplication",
				uiSkin);

		// Set the checkbox to checked if multiplication is enabled in preferences
		enableMultiplication.setChecked(PreferencesManager.getInstance().getBoolean(
				"showMultiplicationProblems"));

		// Update the preferences if the checkbox is checked or unchecked.
		enableMultiplication.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				PreferencesManager.getInstance().set("showMultiplicationProblems",
						enableAddition.isChecked());
			}
		});

		mainContainer.add(enableMultiplication).row();

		// Button to return back to menu
		TextButton backToMenuButton = new TextButton("Back to Menu", uiSkin);

		backToMenuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// Save the preferences (if any were changed)
				PreferencesManager.getInstance().savePreferences();
				// Play click sound effect
				AssetManager.getInstance().getSound("button-click").play();
				dispose(); // Dispose of this class' resources
				gameInstance.setScreen(new MenuScreen(gameInstance)); // Switch screen
				return;
			}
		});

		// Center it throughout the 3 columns.
		mainContainer.add(backToMenuButton).colspan(3);

		stage.addActor(mainContainer);
	}

	@Override
	public void render(float delta) {
		// Draw the UI widgets
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		// Dispose of all UI widgets and listeners
		stage.dispose();
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
