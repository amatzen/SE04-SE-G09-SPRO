package dk.sdu.mmmi.swe.gtg.music.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicPlugin implements IPlugin {

    public static Music music;

    @Override
    public void start(IEngine engine, GameData gameData) {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/GTA-SA.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        music.stop();
    }
}
