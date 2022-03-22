package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dk.sdu.mmmi.cbse.topdowncar.game.CarGame;

public class Main {
	
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration cfg =
			new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("Asteroids");
		cfg.setWindowedMode(1280, 720);
		cfg.setResizable(true);
		
		new Lwjgl3Application(new CarGame(), cfg);
		
	}
	
}
