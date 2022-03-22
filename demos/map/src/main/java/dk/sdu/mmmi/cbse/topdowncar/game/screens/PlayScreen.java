package dk.sdu.mmmi.cbse.topdowncar.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.cbse.topdowncar.game.entities.Car;
import dk.sdu.mmmi.cbse.topdowncar.game.tools.MapLoader;

import static dk.sdu.mmmi.cbse.topdowncar.game.Constants.*;
import static dk.sdu.mmmi.cbse.topdowncar.game.entities.Car.*;

public class PlayScreen implements Screen {

    private static final float CAMERA_ZOOM = 0.3f;
    private final World mWorld;
    // private final Viewport mViewport;
    private final Car mPlayer;
    private final MapLoader mMapLoader;
    private final TiledMap map;
    private final AssetManager manager;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera mCamera;
    private int tileWidth, tileHeight, mapWidthInTiles, mapHeightInTiles, mapWidthInPixels, mapHeightInPixels;


    public PlayScreen() {
        // Map loading
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("demo.tmx", TiledMap.class);
        manager.finishLoading();

        map = manager.get("demo.tmx", TiledMap.class);

        MapProperties properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        mCamera = new OrthographicCamera(320.f, 180.f);
        mCamera.position.x = mapWidthInPixels * .5f;
        mCamera.position.y = mapHeightInPixels * .45f;

        renderer = new OrthogonalTiledMapRenderer(map);

        // mViewport = new FitViewport(RESOLUTION.x / PPM, RESOLUTION.y / PPM, mCamera);
        mWorld = new World(GRAVITY, true);
        mMapLoader = new MapLoader(mWorld);
        mPlayer = new Car(20f, 0.8f, 10, mMapLoader, DRIVE_4WD, mWorld);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        update(delta);
        draw();

        mCamera.update();
        renderer.setView(mCamera);
        renderer.render();

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mPlayer.setDriveDirection(DRIVE_DIRECTION_FORWARD);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mPlayer.setDriveDirection(DRIVE_DIRECTION_BACKWARD);
        } else {
            mPlayer.setDriveDirection(DRIVE_DIRECTION_NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mPlayer.setTurnDirection(TURN_DIRECTION_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mPlayer.setTurnDirection(TURN_DIRECTION_RIGHT);
        } else {
            mPlayer.setTurnDirection(TURN_DIRECTION_NONE);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            mCamera.zoom -= CAMERA_ZOOM;
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            mCamera.zoom += CAMERA_ZOOM;
        }
    }


    private void draw() {
    }

    private void update(final float delta) {
        mPlayer.update(delta);
        mCamera.position.set(mPlayer.getBody().getPosition(), 0);
        mCamera.update();
        mWorld.step(delta, VELOCITY_ITERATION, POSITION_ITERATION);
    }

    @Override
    public void resize(int width, int height) {
        mCamera.viewportWidth = width;
        mCamera.viewportHeight = height;
        mCamera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        mWorld.dispose();
        mMapLoader.dispose();
    }
}
