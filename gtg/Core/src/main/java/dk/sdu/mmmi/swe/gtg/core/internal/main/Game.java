package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component(immediate = true)
public class Game implements ApplicationListener {

    private OrthographicCamera cam;
    private Box2DDebugRenderer mB2dr;

    @Reference
    private IWorldManager worldManager;

    @Reference
    private IEngine engine;

    private float PPM = 20;

    private final GameData gameData = new GameData();

    private List<IGamePluginService> entityPlugins = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> pluginsToBeStarted = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> pluginsToBeStopped = new CopyOnWriteArrayList<>();

    public Game() {
        System.out.println("Game created");
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

        mB2dr = new Box2DDebugRenderer();
        cam = new OrthographicCamera(gameData.getDisplayWidth() / PPM, gameData.getDisplayHeight() / PPM);
        cam.position.set(0, 0, 0);
        cam.update();

        gameData.setCamera(cam);
        gameData.setSpriteBatch(new SpriteBatch());

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
    }

    @Override
    public void render() {
        pluginsToBeStarted.forEach(plugin -> plugin.start(engine, gameData));
        pluginsToBeStarted.clear();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();

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

    private void update() {
        engine.update(gameData);
    }

    private void draw() {
        worldManager.render(mB2dr, cam.combined);
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
}
