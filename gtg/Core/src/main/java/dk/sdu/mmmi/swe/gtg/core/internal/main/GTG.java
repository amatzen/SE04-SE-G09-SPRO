package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GTG extends Game {

    private final Map<String, ScreenSPI> screens = new ConcurrentHashMap<>();

    @Reference
    private ScreenManagerSPI screenManager;

    private Screen currentScreen;
    private ISignalListener<String> onScreenChangeListener = (signal, value) -> {
        ScreenSPI screen = this.screens.get(value);

        if (screen == null) {
            System.err.println("No screen with name " + value + " found");
            return;
        }

        if (Objects.nonNull(currentScreen)) {
            currentScreen.dispose();
        }

        this.currentScreen = screen;
        setScreen(screen);
    };

    public GTG() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg =
                new LwjglApplicationConfiguration();
        cfg.title = "Grand Theft Gørding";
        cfg.width = 1600;
        cfg.height = 900;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        screenManager.addOnScreenChangeListener(onScreenChangeListener);

        GameData gameData = screenManager.getGameData();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        screenManager.changeScreen("SplashScreen");
    }

    @Override
    public void render() {
        super.render();

        GameData gameData = screenManager.getGameData();

    }

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
    public void dispose() {
    }

    @Reference(
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeScreen"
    )
    public void addScreen(ScreenSPI screen) {
        this.screens.put(screen.getClass().getSimpleName(), screen);
    }

    public void removeScreen(ScreenSPI screen) {
        this.screens.remove(screen.getClass().getSimpleName());
    }
}
