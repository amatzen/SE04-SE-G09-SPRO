package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;
import dk.sdu.mmmi.swe.gtg.core.internal.screens.SplashScreen;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component(immediate = true)
public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public final GameData gameData = new GameData();

    private List<IGamePluginService> entityPlugins = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> pluginsToBeStarted = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> pluginsToBeStopped = new CopyOnWriteArrayList<>();

    @Reference
    private IEngine engine;

    @Reference
    private IWorldManager worldManager;

    public Game() {
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
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        setScreen(new SplashScreen(this));

    }

    @Override
    public void render() {
        super.render();
        
        pluginsToBeStarted.forEach(plugin -> plugin.start(engine, gameData));
        pluginsToBeStarted.clear();

        pluginsToBeStopped.forEach(plugin -> plugin.stop(engine, gameData));
        pluginsToBeStopped.clear();
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

    private Collection<? extends IGamePluginService> getPluginServices() {
        return entityPlugins;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.add(plugin);
        this.pluginsToBeStarted.add(plugin);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.remove(plugin);
        this.pluginsToBeStopped.add(plugin);
    }

    public IEngine getEngine() {
        return engine;
    }

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public void removeEngine(IEngine systemManager) {
        this.engine = null;
    }

    public IWorldManager getWorldManager() {
        return worldManager;
    }

    public void setWorldManager(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public void removeWorldManager() {
        this.worldManager = null;
    }
}
