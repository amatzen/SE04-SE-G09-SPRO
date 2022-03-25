package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Splash implements Screen {
    private final SpriteBatch batch;
    private final Texture splashTexture;
    private final Sprite sprite;

    public Splash(Game game) {
        super();
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