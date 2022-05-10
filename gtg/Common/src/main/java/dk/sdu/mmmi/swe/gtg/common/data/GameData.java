package dk.sdu.mmmi.swe.gtg.common.data;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameData {

    private final GameKeys keys = new GameKeys();
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private float delta;
    private int displayWidth;
    private int displayHeight;

    public GameKeys getKeys() {
        return keys;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}
