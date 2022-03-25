package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;

public class GameScreen extends ScreenAdapter implements Screen {
    private final Game game;
    private final GameData gameData;

    private final float PPM = 20;

    private OrthographicCamera cam;
    private Box2DDebugRenderer mB2dr;
    private IWorldManager worldManager;

    public GameScreen(Game game) {
        this.game = game;
        this.gameData = game.gameData;

        mB2dr = new Box2DDebugRenderer();
        cam = new OrthographicCamera(gameData.getDisplayWidth() / PPM, gameData.getDisplayHeight() / PPM);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        worldManager.update(gameData.getDelta());
        worldManager.render(mB2dr, cam.combined);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();

        cam.position.set(0, 0, 0);
        cam.update();

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
