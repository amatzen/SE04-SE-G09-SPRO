package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;

    private final GameData gameData = new GameData();

    private List<IGamePluginService> entityPlugins = new CopyOnWriteArrayList<>();

    private IEngine engine;

    public Game() {
        init();
    }

    public void init() {
        LwjglApplicationConfiguration cfg =
                new LwjglApplicationConfiguration();
        cfg.title = "GTG";
        cfg.width = 500;
        cfg.height = 400;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

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
    }

    private void draw() {

    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return entityPlugins;
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.add(plugin);
        plugin.start(gameData);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.remove(plugin);
        plugin.stop(gameData);
    }

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public void removeEngine(IEngine systemManager) {
        this.engine = null;
    }
}
