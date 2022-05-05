package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.gameover.GameOver;
import org.osgi.service.component.annotations.Component;

@Component
public class GameOverControlSystem implements IPostEntityProcessingService {

    GameOver gameOver;

    @Override
    public void addedToEngine(IEngine engine) {
    }

    @Override
    public void process(GameData gameData) {

        if (gameData.getKeys().isPressed(GameKeys.P)) {
            gameOver = new GameOver();
        }

        if (gameData.getKeys().isDown(GameKeys.P)) {
            gameOver.getStage().getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());
            gameOver.getStage().draw();
            System.out.println("Drawn");
        }
    }
}
