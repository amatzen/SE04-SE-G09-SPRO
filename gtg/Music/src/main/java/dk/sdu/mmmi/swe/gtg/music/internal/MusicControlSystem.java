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
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class MusicControlSystem implements IProcessingSystem, IPlugin {

    private Entity player;
    private Boolean isPaused = false;

    private IEntityListener playerListener;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
    }

    @Override
    public void process(GameData gameData) {
        if (player != null) {
            WantedPart wantedPart = player.getPart(WantedPart.class);
            int totalWanted = wantedPart.getWantedLevel();

        if (totalWanted > 0 && !(isPaused)) {
            MusicPlugin.policeSound.play();
        } else if (totalWanted == 0 || isPaused) {
            MusicPlugin.policeSound.pause();
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

    @Override
    public void install(GameData gameData) {
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
