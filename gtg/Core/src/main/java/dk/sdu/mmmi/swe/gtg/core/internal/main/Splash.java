package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Splash implements Screen {
    private final AssetManager assetManager;

    private final SpriteBatch spriteBatch;
    private final Texture splashTexture;

    public Splash(AssetManager assetManager) {
        super();
        this.assetManager = assetManager;
        this.splashTexture = this.assetManager.get("splash_screen.png", Texture.class);
        this.spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(splashTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

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
        spriteBatch.dispose();
        splashTexture.dispose();
    }
}
