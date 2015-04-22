package com.tripointgames.problematic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tripointgames.problematic.Main;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Problematic";
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
