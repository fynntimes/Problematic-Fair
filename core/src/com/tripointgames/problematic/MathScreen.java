package com.tripointgames.problematic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.tripointgames.problematic.util.AssetManager;
import com.tripointgames.problematic.util.MathProblem;

/**
 * The screen shown when the player dies or moves onto the next level. This is
 * the screen that shows a math equation, and allows the user to choose their
 * answer.
 * 
 * @author Faizaan Datoo
 */
public class MathScreen implements Screen {

	Main gameInstance;

	Stage stage;
	Skin skin;
	TextButtonStyle wrongAnswerStyle; // Button style with red text
	Table mainContainer;

	SpriteBatch fontBatch = new SpriteBatch();
	BitmapFont debugFont = new BitmapFont();
	
	MathProblem mathProblem;

	Label directionsLabel, equationsLabel;
	TextButton answer1, answer2, answer3;

	public MathScreen(Main gameInstance) {
		this.gameInstance = gameInstance;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage); // Allow stage to receive input

		createUISkin();

		mainContainer = new Table(skin);
		mainContainer.setFillParent(true); // Fill the whole scren
		mainContainer.setBackground(AssetManager.getInstance()
				.convertTextureToDrawable("mathscreenBackground"));
		stage.addActor(mainContainer);

		// Shown at the top of the screen
		directionsLabel = new Label(
				"You fell off! Solve this math problem to respawn.", skin, "smallLabel");
		mainContainer.add(directionsLabel).align(Align.center).top().expandX().row();

		// Randomly generate a math problem
		mathProblem = new MathProblem();

		// Add the equation
		equationsLabel = new Label(mathProblem.equation + " = ?", skin, "largeLabel");
		mainContainer.add(equationsLabel).align(Align.center).row();

		// Add the answers
		HorizontalGroup answers = new HorizontalGroup();

		answer1 = new TextButton(String.valueOf(mathProblem.answers[0]), skin);
		answer1.setName("0"); // Store the answer ID this button represents
		answer1.padRight(200); // Allow a 200 pixel padding from the next button
		answer1.addListener(buttonChangeListener); // Add the listener

		answer2 = new TextButton(String.valueOf(mathProblem.answers[1]), skin);
		answer2.setName("1");
		answer2.padRight(200);
		answer2.addListener(buttonChangeListener);

		answer3 = new TextButton(String.valueOf(mathProblem.answers[2]), skin);
		answer3.setName("2");
		answer3.padRight(200);
		answer3.addListener(buttonChangeListener);

		answers.addActor(answer1);
		answers.addActor(answer2);
		answers.addActor(answer3);

		mainContainer.add(answers).padLeft(100).padTop(100).center().expandX();
	}

	@Override
	public void render(float delta) {
		// Update and draw the stage
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void hide() {
		// Dispose of all buttons (this prevents buttons from being active when
		// they shouldn't be)
		stage.dispose();
	}

	@Override
	public void dispose() {
		// Dispose of all resources
		stage.dispose();
	}

	/**
	 * Create the skin for this UI. The skin determines the fonts and colors
	 * used by each UI widget, such as labels and text buttons.
	 */
	private void createUISkin() {
		skin = new Skin();

		// Add the fonts
		BitmapFont smallerFont = new BitmapFont(Gdx.files.internal("skin/fonts/chalkboard-font.fnt"));
		smallerFont.setScale(0.75f); // Make this font 75% of its original size.
		skin.add("smallerFont", smallerFont);
		
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

		// Style all small labels
		LabelStyle smallLabelStyle = new LabelStyle();
		smallLabelStyle.font = skin.getFont("smallerFont");
		skin.add("smallLabel", smallLabelStyle);
		
		// Style all labels
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("regularFont");
		skin.add("default", labelStyle);

		// Style all large labels
		LabelStyle largeLabelStyle = new LabelStyle();
		largeLabelStyle.font = skin.getFont("largeFont");
		skin.add("largeLabel", largeLabelStyle);

		// Style all text buttons
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = skin.getFont("largeFont");
		skin.add("default", textButtonStyle);

		// Initialize wrong answer style
		wrongAnswerStyle = new TextButtonStyle();
		wrongAnswerStyle.font = skin.getFont("largeFont");
		wrongAnswerStyle.fontColor = Color.RED;
	}

	/**
	 * This is added to all buttons. This validates the answer. If it is
	 * correct, it switches to the GameScreen. If it is incorrect, it displays
	 * the unsuccessful message.
	 */
	private ChangeListener buttonChangeListener = new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			int answerID = Integer.parseInt(actor.getName());
			if (mathProblem.validateAnswer(answerID)) {
				// Play sound effect
				AssetManager.getInstance().getSound("correct-answer").play();
				// Return to the level
				gameInstance.setScreen(new GameScreen(gameInstance));
			} else {
				// Change the UI to indicate that it was the wrong answer
				directionsLabel.setText("Uh oh! Try again.");
				directionsLabel.setColor(Color.RED);
				((TextButton) actor).setStyle(wrongAnswerStyle);
				// Play sound effect
				AssetManager.getInstance().getSound("wrong-answer").play();
			}
		}
	};

	/*
	 * The following methods are unused but are required by the Screen
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

}
