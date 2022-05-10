package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dk.sdu.mmmi.swe.gtg.core.internal.main.Game;
import dk.sdu.mmmi.swe.gtg.core.internal.managers.ScreenManager;

public class GameOver implements Screen {

    private final Game game;
    private final Stage stage;

    private Label deathMessage;
    private Label moneyLabel;
    private Label totalMoney;
    private int money = 0;

    private Image gmLogo;

    public GameOver(Game game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skinBtn = new Skin(Gdx.files.internal("skins/craftacular/craftacular-ui.json"));

        // Game over logo
        gmLogo = new Image(new Texture(Gdx.files.internal("assets/game-over-white.png")));
        gmLogo.setAlign(Align.top);
        table.add(gmLogo).size(400, 400).row();

        // Death message
        deathMessage = new Label("You got busted and lost all your money!", new Label.LabelStyle(
                new BitmapFont(), Color.RED));
        table.add(deathMessage).pad(25).row();

        // Show statistics
        moneyLabel = new Label("Total Money Stolen: ", new Label.LabelStyle(
                new BitmapFont(), Color.WHITE));
//        moneyLabel.setFontScale(1.3f);
        moneyLabel.setAlignment(Align.center);
        table.add(moneyLabel).row();

        totalMoney = new Label("$" + String.format("%06d", money), new Label.LabelStyle(
                new BitmapFont(), Color.WHITE));
//        totalMoney.setFontScale(1.3f);
        totalMoney.setAlignment(Align.center);
        table.add(totalMoney).row();

        // Restart button
        TextButton btnStart = new TextButton("Restart", skinBtn);
        btnStart.align(Align.bottom);
        table.add(btnStart).pad(75).row();

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

