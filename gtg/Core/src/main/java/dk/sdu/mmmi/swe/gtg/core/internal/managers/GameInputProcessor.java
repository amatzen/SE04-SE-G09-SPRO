package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        if (k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if (k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if (k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if (k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, true);
        }

        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, true);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, true);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, true);
        }
        if (k == Keys.M) {
            gameData.getKeys().setKey(GameKeys.M, true);
        }
        if (k == Keys.P) {
            gameData.getKeys().setKey(GameKeys.P, true);
        }
        if (k == Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, true);
        }
        if (k == Keys.K) {
            gameData.getKeys().setKey(GameKeys.K, true);
        }
        if (k == Keys.G) {
            gameData.getKeys().setKey(GameKeys.G, true);
        }
        if (k == Keys.H) {
            gameData.getKeys().setKey(GameKeys.H, true);
        }

        if (k == Keys.Q) {
            gameData.getKeys().setKey(GameKeys.Q, true);
        }

        return true;
    }

    public boolean keyUp(int k) {
        if (k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if (k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if (k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if (k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, false);
        }

        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, false);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, false);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, false);
        }
        if (k == Keys.M) {
            gameData.getKeys().setKey(GameKeys.M, false);
        }
        if (k == Keys.P) {
            gameData.getKeys().setKey(GameKeys.P, false);
        }
        if (k == Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, false);
        }
        if (k == Keys.K) {
            gameData.getKeys().setKey(GameKeys.K, false);
        }
        if (k == Keys.G) {
            gameData.getKeys().setKey(GameKeys.G, false);
        }
        if (k == Keys.H) {
            gameData.getKeys().setKey(GameKeys.H, false);
        }

        if (k == Keys.Q) {
            gameData.getKeys().setKey(GameKeys.Q, false);
        }

        return true;
    }

}








