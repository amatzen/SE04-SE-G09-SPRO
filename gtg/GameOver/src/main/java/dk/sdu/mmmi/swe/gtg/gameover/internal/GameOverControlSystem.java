package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class GameOverControlSystem implements IPostProcessingSystem {

    public static Music wastedSound;
    List<? extends Entity> entity;
    private LifePart playerLife;
    private boolean gameOver = false;

    @Reference
    private ScreenManagerSPI screenManager;

    @Override
    public void addedToEngine(IEngine engine) {
        entity = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get());

        wastedSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Wasted-sound.mp3"));
        wastedSound.setLooping(false);
        wastedSound.setVolume(0.3f);
    }

    @Override
    public void process(GameData gameData) {
        if (gameOver) return;

        if (playerLife != null) {
            if (playerLife.getLife() <= 0) {
                this.gameOver = true;
                this.screenManager.changeScreen("GameOverScreen");
                wastedSound.play();
            }
        }

        if (gameData.getKeys().isPressed(GameKeys.K)) {
            this.gameOver = true;
            this.screenManager.changeScreen("GameOverScreen");
            wastedSound.play();
        }

        for (Entity i : entity) {
            playerLife = i.getPart(LifePart.class);
        }
    }
}
