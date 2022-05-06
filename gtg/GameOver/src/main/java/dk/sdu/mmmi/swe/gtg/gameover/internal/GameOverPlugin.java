package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
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
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commongameover.GameOverSPI;
import org.osgi.service.component.annotations.Component;

@Component
public class GameOverPlugin implements IPlugin, GameOverSPI {

    private Stage stage;

    private Label deathMessage;
    private Label moneyLabel;
    private Label totalMoney;
    private int money = 0;

    private Image gmLogo;

    @Override
    public void install(IEngine engine, GameData gameData) {
        this.stage = new Stage(new ScreenViewport());

        // Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

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
                //game.setScreen(new GameScreen(game));
                System.out.println("Im dead yes");
            }
        });
        stage.getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        stage.addActor(table);
        stage.draw();
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {

    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Stage setStage() {
        return this.stage;
    }
}
