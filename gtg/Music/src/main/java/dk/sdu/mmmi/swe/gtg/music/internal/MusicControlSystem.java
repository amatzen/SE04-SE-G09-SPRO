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

    private IEntityListener playerListener;

    private float crimeLevel;

    @Override
    public void addedToEngine(IEngine engine) {
        crimeLevel = 0;

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
    public void process(GameData gameData) {

        if (gameData.getKeys().isPressed(GameKeys.UP)) {
            if (MusicPlugin.MenuMusic.isPlaying()) {
                MusicPlugin.MenuMusic.stop();
                MusicPlugin.GameSound.play();
                MusicPlugin.PoliceSound.play();
            }
        }

        if (gameData.getKeys().isPressed(GameKeys.M)) {
            if (MusicPlugin.GameSound.isPlaying()) {
                MusicPlugin.GameSound.pause();
            } else {
                MusicPlugin.MenuMusic.stop();
                MusicPlugin.GameSound.play();
            }
            /*
            if (MusicPlugin.PoliceSound.isPlaying()) {
                MusicPlugin.PoliceSound.pause();
            } else {
                MusicPlugin.MenuMusic.stop();
                MusicPlugin.PoliceSound.play();
            }

             */
        }

        WantedPart wantedPart = player.getPart(WantedPart.class);
        int totalWanted = wantedPart.getWantedLevel();

        if (totalWanted > 0) {
            MusicPlugin.PoliceSound.play();
        } else if (totalWanted == 0){
            MusicPlugin.PoliceSound.stop();
        }
    }
}
