package dk.sdu.mmmi.swe.gtg.music.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicPlugin implements IPlugin {

    public static Music MenuMusic;
    public static Music GameSound;
    public static Music PoliceSound;

    @Override
    public void install(IEngine engine, GameData gameData) {
        menuMusic();
        gameSound();
        policeSound();
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        MenuMusic.stop();
        GameSound.stop();
        PoliceSound.stop();
    }

    public void menuMusic() {
        MenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/GTA-SA.mp3"));
        MenuMusic.setLooping(true);
        MenuMusic.setVolume(0.5f);
        MenuMusic.play();
    }

    public void gameSound() {
        GameSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Traffic.mp3"));
        GameSound.setLooping(true);
        GameSound.setVolume(0.01f);
    }

    public void policeSound() {
        PoliceSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Police.mp3"));
        PoliceSound.setLooping(true);
        PoliceSound.setVolume(0.05f);
    }

    public void setGameMusic() {
        MenuMusic.stop();
        GameSound.play();
    }

}
