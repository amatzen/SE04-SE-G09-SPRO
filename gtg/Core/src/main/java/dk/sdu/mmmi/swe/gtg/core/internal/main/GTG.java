package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class GTG extends Game {
    private final List<IPlugin> entityPlugins = new CopyOnWriteArrayList<>();
    private final List<IPlugin> pluginsToBeInstalled = new CopyOnWriteArrayList<>();
    private final List<IPlugin> pluginsToBeUninstalled = new CopyOnWriteArrayList<>();
    private final Map<String, ScreenSPI> screens = new ConcurrentHashMap<>();

    @Reference
    private ScreenManagerSPI screenManager;

    private Screen currentScreen;
    private ISignalListener<String> onScreenChangeListener = (signal, value) -> {
        ScreenSPI screen = this.screens.get(value);


        if (screen == null) {
            System.out.println("No screen with name " + value + " found");
            return;
        }

        if (Objects.nonNull(currentScreen)) {
            currentScreen.dispose();
        }

        this.currentScreen = screen;
        setScreen(screen);
    };

    public GTG() {
        System.out.println("Game created");
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

        pluginsToBeInstalled.forEach(plugin -> plugin.install(gameData));
        pluginsToBeInstalled.clear();

        pluginsToBeUninstalled.forEach(plugin -> plugin.uninstall(gameData));
        pluginsToBeUninstalled.clear();
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

    private Collection<? extends IPlugin> getPluginServices() {
        return entityPlugins;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addGamePluginService(IPlugin plugin) {
        this.entityPlugins.add(plugin);
        this.pluginsToBeInstalled.add(plugin);
    }

    public void removeGamePluginService(IPlugin plugin) {
        this.entityPlugins.remove(plugin);
        this.pluginsToBeUninstalled.add(plugin);
    }

    @Reference(
        cardinality = ReferenceCardinality.MULTIPLE,
        policy = ReferencePolicy.DYNAMIC,
        unbind = "removeScreen"
    )
    public void addScreen(ScreenSPI screen) {
        this.screens.put(screen.getClass().getSimpleName(), screen);
        System.out.println("Loaded screen: " + screen.getClass().getSimpleName());
    }

    public void removeScreen(ScreenSPI screen) {
        this.screens.remove(screen.getClass().getSimpleName());
    }
}
