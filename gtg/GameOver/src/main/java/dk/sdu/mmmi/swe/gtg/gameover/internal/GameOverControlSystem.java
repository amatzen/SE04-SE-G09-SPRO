package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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


    @Override
    public void addedToEngine(IEngine engine) {
        entity = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get());
    }

    @Override
    public void process(GameData gameData) {
        if (playerLife != null) {
            if (playerLife.getLife() <= 0) {
                gameOverSPI.getStage().draw();
            }
        }

        for (Entity i : entity) {
            playerLife = i.getPart(LifePart.class);
        }
    }
}
