package dk.sdu.mmmi.swe.gtg.Hud.internal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

public class HudControlSystem implements IEntityProcessingService {

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    // score/time Tracking Variables
    private float bullets;
    private float life;
    private static float money;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label bulletLabel;
    private Label levelLabel;
    private Label lifeLabel;
    private Label moneyLabel;

    public static final int V_WIDTH = 1600;
    public static final int V_HEIGHT = 900;


    @Override
    public void addedToEngine(IEngine engine) {
        // define our tracking variables
        bullets = 0;
        life = 3;
        money = 0;

        // setup the HUD viewport using a new camera seperate from our gamecam
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());

        // define our stage using that viewport and our games spritebatch
        stage = new Stage(viewport);

        // define a table used to organize our hud's labels
        Table table = new Table();

        // Top-Align table
        table.top();

        // make the table fill the entire stage
        table.setFillParent(true);

        // define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", bullets), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", money), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        bulletLabel = new Label("BULLETS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        moneyLabel = new Label("MONEY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(moneyLabel).expandX().padTop(10);
        table.add(lifeLabel).expandX().padTop(10);
        table.add(bulletLabel).expandX().padTop(10);

        // add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        // add our table to the stage
        stage.addActor(table);
    }

    @Override
    public void process(GameData gameData) {

    }

    public static void addScore(int value){
        money += value;
        scoreLabel.setText(String.format("%06d", money));
    }
}
