package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.GameInputProcessor;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;

public class GameScreen extends ScreenAdapter implements Screen {
    private final Game game;
    private final GameData gameData;

    private final float PPM = 40;

    private final OrthographicCamera cam;
    private final Box2DDebugRenderer mB2dr;
    private final IWorldManager worldManager;
    private Hud hud;
    private SpriteBatch spriteBatch;

    public GameScreen(Game game) {
        spriteBatch = new SpriteBatch();
        hud = new Hud(spriteBatch);

        this.game = game;
        this.gameData = game.gameData;

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        this.mB2dr = new Box2DDebugRenderer();
        this.cam = new OrthographicCamera(gameData.getDisplayWidth() / PPM, gameData.getDisplayHeight() / PPM);
        this.worldManager = this.game.getWorldManager();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        this.game.getEngine().update(this.gameData);
        this.game.getWorldManager().render(mB2dr, cam.combined);

        this.gameData.getKeys().update();

        spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(delta);
        hud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.getStage().getViewport().update(width, height);
    }

    @Override
    public void show() {
        super.show();

        cam.position.set(0, 0, 0);
        cam.update();
        this.game.gameData.setCamera(cam);
        this.game.gameData.setSpriteBatch(new SpriteBatch());

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
