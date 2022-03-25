package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.ClasspathFileHandleResolver;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.core.internal.screens.GameScreen;
import dk.sdu.mmmi.swe.gtg.core.internal.screens.SplashScreen;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game extends com.badlogic.gdx.Game implements ApplicationListener {
    public final GameData gameData = new GameData();

    private List<IGamePluginService> entityPlugins = new CopyOnWriteArrayList<>();

    private IEngine engine;

    public Game() {
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

        /*
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );*/
        setScreen(new SplashScreen(this));

    }

    @Override
    public void render() {
/*
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();*/

        super.render();

        gameData.getKeys().update();
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

    private void update() {
        engine.update(gameData);

        //worldManager.update(gameData.getDelta());
    }

    private void draw() {
        //worldManager.render(mB2dr, cam.combined);
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return entityPlugins;
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.add(plugin);
        plugin.start(engine, gameData);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.remove(plugin);
        plugin.stop(engine, gameData);
    }

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public void removeEngine(IEngine systemManager) {
        this.engine = null;
    }
}
