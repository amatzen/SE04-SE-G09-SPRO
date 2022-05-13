package dk.sdu.mmmi.swe.gtg.gameover.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class GameOverControlSystem implements IPostProcessingSystem, IPlugin {

    private Entity player;
    public Music wastedSound;
    private boolean gameOver = false;

    @Reference
    private ScreenManagerSPI screenManager;

    @Reference
    private IEngine engine;

    private IEntityListener playerListener;

    @Override
    public void addedToEngine() {
        wastedSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Wasted-sound.mp3"));
        wastedSound.setLooping(false);
        wastedSound.setVolume(0.3f);
    }

    @Override
    public void process(GameData gameData) {
        if (gameOver) return;

        if (player == null) return;

        LifePart playerLife = player.getPart(LifePart.class);
        if (playerLife.getLife() <= 0) {
            this.gameOver = true;
            this.screenManager.changeScreen("GameOverScreen");
            wastedSound.play();
        }

        if (gameData.getKeys().isPressed(GameKeys.K)) {
            this.gameOver = true;
            this.screenManager.changeScreen("GameOverScreen");
            wastedSound.play();
        }
    }

    @Override
    public void install(GameData gameData) {
        gameOver = false;
        playerListener = new EntityListener() {
            @Override
            public void onEntityAdded(Entity entity) {
                player = entity;
            }

            @Override
            public void onEntityRemoved(Entity entity) {
                player = null;
            }
        };

        engine.addEntityListener(Family.builder().with(PlayerPart.class).get(), playerListener, true);
    }

    @Override
    public void uninstall(GameData gameData) {
        engine.removeEntityListener(Family.builder().with(PlayerPart.class).get(), playerListener);
    }
}
