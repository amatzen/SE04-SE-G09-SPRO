package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import com.badlogic.gdx.Screen;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;

import java.util.HashMap;

public class ScreenManager {
    private HashMap<Class<? extends Screen>, Screen> screens = new HashMap<>();

    public static synchronized ScreenManager getInstance () {
        if (ScreenManager.instance == null) {
            ScreenManager.instance = new ScreenManager();
        }
        return ScreenManager.instance;
    }
    private static ScreenManager instance = null;
    private ScreenManager() {
        System.out.println("ScreenManager created");
    }


    private Game game;
    public void setGame(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return game;
    }

    public void setScreen(Class<? extends Screen> screenClass) {
        if (screens.containsKey(screenClass)) {
            game.setScreen(screens.get(screenClass));
        } else {
            try {
                Screen screen = screenClass.newInstance();
                this.screens.put(screenClass, screen);
                game.setScreen(screen);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
