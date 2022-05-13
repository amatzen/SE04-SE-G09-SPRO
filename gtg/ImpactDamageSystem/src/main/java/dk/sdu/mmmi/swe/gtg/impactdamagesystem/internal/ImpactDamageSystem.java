package dk.sdu.mmmi.swe.gtg.impactdamagesystem.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ImpactDamageSystem implements IPlugin {

    @Reference
    private CollisionSPI collisionSPI;

    private ICollisionListener collisionListener;

    public static Music crashSound;


    @Override
    public void install(GameData gameData) {
        collisionListener = new ImpactDamageCollisionListener();
        collisionSPI.addListener(collisionListener);

        crashSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Crash.mp3"));
        crashSound.setVolume(0.3f);
    }

    @Override
    public void uninstall(GameData gameData) {
        collisionSPI.removeListener(collisionListener);
    }
}
