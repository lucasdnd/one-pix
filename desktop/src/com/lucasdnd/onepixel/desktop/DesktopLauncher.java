package com.lucasdnd.onepixel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lucasdnd.onepixel.OnePixel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 700;
		config.resizable = false;
		new LwjglApplication(new OnePixel(), config);
	}
	
	/**
	 * Placement sound - DasDeer (freesound.org) http://freesound.org/people/DasDeer/sounds/161544/
	 * Wood cutting sound - f4ngy (freesound.org) http://freesound.org/people/f4ngy/sounds/240788/
	 */
}
