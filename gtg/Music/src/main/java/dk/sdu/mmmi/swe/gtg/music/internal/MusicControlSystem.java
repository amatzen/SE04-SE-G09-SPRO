package dk.sdu.mmmi.swe.gtg.music.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.WantedPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicControlSystem implements IProcessingSystem {

    private Entity player;
    private Boolean isPaused = false;

    @Override
    public void addedToEngine(IEngine engine) {
        float crimeLevel = 0;

        IEntityListener playerListener = new EntityListener() {
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
    public void process(GameData gameData) {
        WantedPart wantedPart = player.getPart(WantedPart.class);
        int totalWanted = wantedPart.getWantedLevel();

        if (totalWanted > 0 && !(isPaused)) {
            MusicPlugin.policeSound.play();
        } else if (totalWanted == 0 || isPaused) {
            MusicPlugin.policeSound.stop();
        }

        if (gameData.getKeys().isPressed(GameKeys.M)) {
            if (MusicPlugin.gameSound.isPlaying() || MusicPlugin.menuMusic.isPlaying()) {
                MusicPlugin.gameSound.pause();
                // MusicPlugin.MenuMusic.pause();
                isPaused = true;
            } else {
                MusicPlugin.gameSound.play();
                // MusicPlugin.MenuMusic.play();
                isPaused = false;
            }
        }
    }
}
