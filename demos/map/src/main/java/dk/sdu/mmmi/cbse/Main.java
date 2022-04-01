package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg =
                new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Grand Theft Gørding");
        cfg.setWindowedMode(800, 800);
        cfg.setResizable(true);

        new Lwjgl3Application(new Demo(), cfg);

    }

}
