package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;


public class SplashScreen implements Screen {
    private final Game game;

    private final SpriteBatch batch;
    private final Texture splashTexture;
    private final Sprite sprite;

    public SplashScreen(Game game) {
        super();
        this.game = game;
        this.batch = new SpriteBatch();
        this.splashTexture = new Texture("assets/splash_screen.png");
        this.sprite = new Sprite(splashTexture);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

    }

    @Override
    public void resize(int i, int i1) {

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
        batch.dispose();
        splashTexture.dispose();
    }
}
