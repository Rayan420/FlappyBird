package com.rayan.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); // setting the frame rate of the app to 60 fps
		config.setTitle(FlappyBird.TITLE); // setting window title
		config.setWindowedMode(FlappyBird.WIDTH, FlappyBird.HEIGHT); // setting window width and height
		new Lwjgl3Application(new FlappyBird(), config);
	}
}
