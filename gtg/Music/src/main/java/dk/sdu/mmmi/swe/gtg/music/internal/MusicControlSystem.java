package dk.sdu.mmmi.swe.gtg.music.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

@Component
public class MusicControlSystem implements IEntityProcessingService {

    private Music music;

    @Override
    public void addedToEngine(IEngine engine) {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/lofi.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void process(GameData gameData) {

    }
}
