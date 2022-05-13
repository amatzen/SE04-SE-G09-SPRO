package dk.sdu.mmmi.swe.gtg.screens.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenSPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class GameScreen extends ScreenAdapter implements ScreenSPI {
    private GameData gameData;

    private final float PPM = 40;
    private GameData gameData;
    private OrthographicCamera cam;
    private Box2DDebugRenderer mB2dr;

    @Reference
    private ScreenManagerSPI screenManager;

    @Reference
    private IEngine engine;

    @Reference
    private IWorldManager worldManager;

    public GameScreen() {
        super();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        this.engine.update(this.gameData);
        this.worldManager.render(mB2dr, cam.combined);

        this.gameData.getKeys().update();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();

        this.gameData = this.screenManager.getGameData();

        this.mB2dr = new Box2DDebugRenderer();
        this.cam = new OrthographicCamera(gameData.getDisplayWidth() / PPM, gameData.getDisplayHeight() / PPM);

        this.screenManager.setGameInputProcessor();

        cam.position.set(0, 0, 0);
        cam.update();
        this.gameData.setCamera(cam);
        this.gameData.setSpriteBatch(new SpriteBatch());

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}