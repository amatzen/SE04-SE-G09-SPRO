package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.World;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();

    private World world = new World();

    private List<IEntityProcessingService> entityProcessors = new CopyOnWriteArrayList<>();
    private List<IPostEntityProcessingService> entityPostProcessors = new CopyOnWriteArrayList<>();
    private List<IGamePluginService> entityPlugins = new CopyOnWriteArrayList<>();

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

        sr = new ShapeRenderer();

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
        getEntityProcessingServices().forEach(entityProcessor -> {
            entityProcessor.process(gameData, world);
        });

        getPostEntityProcessingServices().forEach(entityPostProcessor -> {
            entityPostProcessor.process(gameData, world);
        });
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Filled);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                 i < shapex.length;
                 j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return entityPlugins;
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return entityProcessors;
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return entityPostProcessors;
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.add(plugin);
        plugin.start(gameData, world);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.entityPlugins.remove(plugin);
        plugin.stop(gameData, world);
    }

    public void addEntityProcessingService(IEntityProcessingService service) {
        this.entityProcessors.add(service);
    }

    public void removeEntityProcessingService(IEntityProcessingService service) {
        this.entityProcessors.remove(service);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService service) {
        this.entityPostProcessors.add(service);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService service) {
        this.entityPostProcessors.remove(service);
    }

}
