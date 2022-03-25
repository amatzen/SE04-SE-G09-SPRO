package dk.sdu.mmmi.swe.gtg.map.internal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map implements ApplicationListener {

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    float unitScale = 1 / 32f;

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 128, 128);
        camera.update();

        map = new TmxMapLoader().load("GTG-Map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 200f;
        camera.viewportHeight = 200f * height/width;
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
