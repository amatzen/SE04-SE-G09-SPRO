package dk.sdu.mmmi.swe.gtg.core.internal.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Splash implements Screen {
    private final Game game;

    private Sprite sprite;

    public Splash(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        Texture splash = new Texture(new FileHandle(Game.class.getClassLoader().getResource("splash.png").getFile()));
        sprite = new Sprite(splash);
    }

    @Override
    public void render(float v) {

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

    }
}
