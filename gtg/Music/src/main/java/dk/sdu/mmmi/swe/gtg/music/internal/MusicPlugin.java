package dk.sdu.mmmi.swe.gtg.music.internal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicPlugin implements IPlugin {

    public static Music MenuMusic;
    public static Music GameMusic;

    @Override
    public void install(IEngine engine, GameData gameData) {
        menuMusic();
        gameMusic();
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        MenuMusic.stop();
        GameMusic.stop();
    }

    public void menuMusic() {
        MenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/GTA-SA.mp3"));
        MenuMusic.setLooping(true);
        MenuMusic.setVolume(0.5f);
        MenuMusic.play();
    }

    public void gameMusic() {
        GameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Traffic.mp3"));
        GameMusic.setLooping(true);
        GameMusic.setVolume(0.01f);
    }

    public void setGameMusic() {
        MenuMusic.stop();
        GameMusic.play();
    }

}
