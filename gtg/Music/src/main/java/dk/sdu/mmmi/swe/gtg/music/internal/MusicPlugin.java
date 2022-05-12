package dk.sdu.mmmi.swe.gtg.music.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicPlugin implements IPlugin {

    public static Music menuMusic;
    public static Music gameSound;
    public static Music policeSound;

    @Override
    public void install(IEngine engine, GameData gameData) {
        menuMusic();
        gameSound();
        policeSound();
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        menuMusic.stop();
        gameSound.stop();
        policeSound.stop();
    }

    public void menuMusic() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/GTA-SA.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        // MenuMusic.play();
    }

    public void gameSound() {
        gameSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Traffic.mp3"));
        gameSound.setLooping(true);
        gameSound.setVolume(0.01f);
        gameSound.play();
    }

    public void policeSound() {
        policeSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Police.mp3"));
        policeSound.setLooping(true);
        policeSound.setVolume(0.25f);
    }

}
