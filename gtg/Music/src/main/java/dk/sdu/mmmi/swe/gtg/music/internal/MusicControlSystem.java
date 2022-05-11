package dk.sdu.mmmi.swe.gtg.music.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicControlSystem implements IProcessingSystem {


    @Override
    public void addedToEngine(IEngine engine) {

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
            if (MusicPlugin.PoliceSound.isPlaying()) {
                MusicPlugin.PoliceSound.pause();
            } else {
                MusicPlugin.MenuMusic.stop();
                MusicPlugin.PoliceSound.play();
            }
        }
    }
}
