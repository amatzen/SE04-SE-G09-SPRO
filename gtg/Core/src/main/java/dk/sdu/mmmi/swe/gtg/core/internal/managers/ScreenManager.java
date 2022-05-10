package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import com.badlogic.gdx.Screen;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;

@Component
public class ScreenManager {
    private HashMap<Class<? extends Screen>, Screen> screens = new HashMap<>();

    private ScreenManager() {
    }

    @Reference
    private Game game;

    public void setScreen(Class<? extends Screen> screenClass) {
        if (screens.containsKey(screenClass)) {
            game.setScreen(screens.get(screenClass));
        } else {
            try {
                Screen screen = screenClass.getDeclaredConstructor().newInstance();
                game.setScreen(screen);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
