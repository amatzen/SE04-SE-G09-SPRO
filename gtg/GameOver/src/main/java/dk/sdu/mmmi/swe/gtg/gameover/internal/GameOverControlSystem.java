package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commongameover.GameOverSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class GameOverControlSystem implements IPostProcessingSystem {

    List<? extends Entity> entity;

    private LifePart playerLife;

    @Reference
    private GameOverSPI gameOverSPI;

    public static Music wastedSound;

    @Override
    public void addedToEngine(IEngine engine) {
        entity = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get());

        wastedSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Wasted-sound-extended.mp3"));
        wastedSound.setLooping(false);
        wastedSound.setVolume(0.3f);
    }

    @Override
    public void process(GameData gameData) {
        if (playerLife != null) {
            if (playerLife.getLife() <= 0) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                Gdx.input.setInputProcessor(gameOverSPI.getStage());

                wastedSound.play();

                // gameOverSPI.getStage().getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());
                gameOverSPI.getStage().act();
                gameOverSPI.getStage().draw();
            }
        }

        for (Entity i : entity) {
            playerLife = i.getPart(LifePart.class);
        }
    }
}
