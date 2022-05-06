package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.gameover.GameOver;
import org.osgi.service.component.annotations.Component;

@Component
public class GameOverControlSystem implements IPostEntityProcessingService {

    GameOver gameOver;
    Boolean dead = false;

    @Override
    public void addedToEngine(IEngine engine) {
        // hvis nogen sker, then
        gameOver = new GameOver();
        dead = true;
    }

    @Override
    public void process(GameData gameData) {
        if (dead) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameOver.getStage().getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());
            gameOver.getStage().draw();
        }
    }
}
