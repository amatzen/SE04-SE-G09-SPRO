package dk.sdu.mmmi.swe.gtg.core.internal.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable {
    // Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private final Viewport viewport;

    // Tracking Variables
    private Integer bullets;
    private Integer health;
    private Integer money;

    // Scene2D widgets
    private final Label showBullets;
    private final Label showMoney;
    private final Label bulletLabel;
    private final Label showHealth;
    private final Label healthLabel;
    private final Label moneyLabel;

    public static final int V_WIDTH = 1600;
    public static final int V_HEIGHT = 900;

    public Hud(SpriteBatch sb) {

        // Define our default tracking variables
        health = 100;
        money = 0;
        bullets = 999;

        // Set up the HUD viewport using a new camera seperate from our gamecam
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());

        // Define our stage using that viewport and our games spritebatch
        stage = new Stage(viewport, sb);

        // Define a table used to organize our hud's labels
        Table table = new Table();

        // Top-Align table
        table.top();

        // Make the table fill the entire stage
        table.setFillParent(true);

        // Define our labels using the String, and a Label style consisting of a font and color
        moneyLabel = new Label("MONEY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        showMoney = new Label("$" + String.format("%06d", money), new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        bulletLabel = new Label("BULLETS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        showBullets = new Label(String.format("%03d", bullets) + "+" , new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        healthLabel = new Label("HEALTH", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        showHealth = new Label(String.format("%01d", health), new Label.LabelStyle(new BitmapFont(), Color.RED));

        // Add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(moneyLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.add(bulletLabel).expandX().padTop(10);

        // Creates a row under
        table.row();

        // Add a second row to our table
        table.add(showMoney).expandX();
        table.add(showHealth).expandX();
        table.add(showBullets).expandX();

        // Add our table to the stage
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void addMoney(int value){
        money += value;
        showMoney.setText(String.format("%06d", money));
    }

    public void removeMoney(int value){
        money -= value;
        showMoney.setText(String.format("%06d", money));
    }

    public void addBullets(int value){
        bullets += value;
        showBullets.setText(String.format("%03d", bullets));
    }

    public void removeBullets(int value){
        bullets -= value;
        showBullets.setText(String.format("%03d", bullets));
    }

    public void gainedHealth(int value){
        health += value;
        showHealth.setText(health);
    }

    public void lostHealth(int value){
        health -= value;
        showHealth.setText(health);
    }

    public Stage getStage() { return stage; }
}