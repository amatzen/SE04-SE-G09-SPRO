package dk.sdu.mmmi.swe.gtg.bustedscreen;

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
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class BustedScreen implements ScreenSPI, Screen {
    @Reference
    private IEngine engine;

    @Reference
    private ScreenManagerSPI screenManager;

    private Stage stage;
    private Label deathMessage;
    private Label moneyLabel;
    private Label totalMoney;
    private int money = 0;

    private Image gmLogo;

    public BustedScreen() {
    }

    public void show() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        PlayerPart player = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get()).get(0).getPart(PlayerPart.class);
        this.money = player.getBalance();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skinBtn = new Skin(Gdx.files.internal("skins/craftacular/craftacular-ui.json"));

        // Game over logo
        gmLogo = new Image(new Texture(Gdx.files.internal("assets/icons/busted.png")));
        gmLogo.setAlign(Align.top);
        table.add(gmLogo).size(400, 100).row();

        // Death message
        deathMessage = new Label("You got busted and lost all your money!", new Label.LabelStyle(
                new BitmapFont(), Color.BLUE));
        deathMessage.setFontScale(1.3f);
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
                engine.reset();
                screenManager.changeScreen("GameScreen");
            }
        });

        // Main Menu button
        TextButton btnMainMenu = new TextButton("Main Menu", skinBtn);
        btnMainMenu.align(Align.bottom);
        table.add(btnMainMenu).pad(75).row();

        btnMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                engine.reset();
                screenManager.changeScreen("MainMenuScreen");

                //ScreenManager.getInstance().setScreen(GameScreen.class);
            }
        });

    }

    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {
        stage.dispose();
    }
}
