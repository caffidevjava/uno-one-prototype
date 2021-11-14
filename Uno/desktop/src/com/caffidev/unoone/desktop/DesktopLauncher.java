package com.caffidev.unoone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.caffidev.unoone.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 0;
		config.vSyncEnabled = false;
		config.title = "Uno";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new Game(), config);
	}
}
