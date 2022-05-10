package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.ScreenManager;

public class MainMenuScreen implements Screen {
    private final Stage stage;

    public MainMenuScreen() {
        this.stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Image logo = new Image(new Texture(Gdx.files.internal("assets/logo.png")));
        table.add(logo).pad(10);

        Skin skinBtn = new Skin(Gdx.files.internal("skins/craftacular/craftacular-ui.json"));
        TextButton btnStart = new TextButton("Start", skinBtn);
        table.add(btnStart).fillX().uniformX().pad(10);

        btnStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ScreenManager.getInstance().setScreen(GameScreen.class);
            }
        });


    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
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
        stage.dispose();
    }
}
