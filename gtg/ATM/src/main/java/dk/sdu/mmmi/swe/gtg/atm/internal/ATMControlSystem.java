package dk.sdu.mmmi.swe.gtg.atm.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncrime.ICrimeAction;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class ATMControlSystem implements IProcessingSystem, IPlugin {
    private static Music cashSound;
    private final IFamily atmFamily;
    private List<? extends Entity> atmEntities;
    @Reference
    private ICrimeAction crimeAction;
    @Reference
    private IWantedLevelSystem wantedLevelSystem;
    @Reference
    private IEngine engine;
    private IEntityListener atmListener;

    public ATMControlSystem() {
        atmFamily = Family.builder().with(ATMBalancePart.class).get();
    }

    @Override
    public void addedToEngine() {

    }

    @Override
    public void process(GameData gameData) {

        this.atmEntities.stream()
                .filter(atmEntity -> atmEntity.getPart(ProximityPart.class).isInProximity())
                .forEach(entity -> {
                    ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
                    timerPart.update(gameData.getDelta());
                });
        this.atmEntities.stream()
                .filter(entity -> entity.getPart(ATMBalancePart.class).isRobbed())
                .forEach(entity -> {
                    ATMRobbedTimer robbedTimer = entity.getPart(ATMRobbedTimer.class);
                    robbedTimer.update(gameData.getDelta());
                });
    }

    @Override
    public void install(GameData gameData) {
        this.atmEntities = engine.getEntitiesFor(
                atmFamily
        );

        atmListener = new EntityListener() {
            @Override
            public void onEntityAdded(Entity entity) {
                ATMTimerPart timerPart = entity.getPart(ATMTimerPart.class);
                timerPart.addAction(5.00, () -> {
                    crimeAction.commit(entity);
                    entity.addPart(getRobbedAtmTexture());
                    cashSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Cash.mp3"));
                    cashSound.setVolume(0.3f);
                    cashSound.play();
                });

                ATMRobbedTimer robbedTimer = entity.getPart(ATMRobbedTimer.class);
                robbedTimer.startTimer();
                robbedTimer.addAction(30.00, () -> {
                    entity.addPart(getAtmTexture());
                    entity.getPart(ATMBalancePart.class).generateBalance();
                    entity.getPart(ATMBalancePart.class).setRobbed(false);
                    robbedTimer.resetTimer();
                    timerPart.resetTimer();
                });
            }

            @Override
            public void onEntityRemoved(Entity entity) {
            }
        };

        engine.addEntityListener(atmFamily, atmListener, true);
    }

    @Override
    public void uninstall(GameData gameData) {
        engine.removeEntityListener(atmFamily, atmListener);
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);
        texturePart.setRegion(textureRegion);
        return texturePart;
    }

    public TexturePart getAtmTexture() {
        return getTexture("assets/entities/atm/atm.png");

    }

    public TexturePart getRobbedAtmTexture() {
        return getTexture("assets/entities/atm/atmRobbed.png");
    }
}
