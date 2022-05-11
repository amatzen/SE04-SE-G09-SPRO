package dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.WantedPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class WantedLevelSystem implements IPlugin, IWantedLevelSystem {

    private float crimeLevel;

    private Entity player;

    private int maxWantedLevel = 5;

    @Reference
    private HudSPI hud;

    private IEntityListener playerListener;

    @Override
    public void install(IEngine engine, GameData gameData) {
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
    public void reportCrime(float weight) {
        this.crimeLevel += weight;
        if (player != null) {
            System.out.println("Crime level: " + crimeLevel);
            WantedPart wantedPart = player.getPart(WantedPart.class);
            wantedPart.setWantedLevel(
                    mapCrimeLevelToWantedLevel(crimeLevel)
            );
            hud.setWantedLevel(wantedPart.getWantedLevel());
        }
    }

    private int mapCrimeLevelToWantedLevel(float crimeLevel) {
        if (crimeLevel <= 0) {
            return 0;
        }

        return (int) Math.min(Math.max(Math.log10(crimeLevel), 1), maxWantedLevel);
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(Family.builder().with(PlayerPart.class).get(), playerListener);
    }
}
