package dk.sdu.mmmi.swe.gtg.hud.internal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class HudControlSystem implements IPostProcessingSystem, HudSPI, IPlugin {
    private Hud hud;
    private SpriteBatch spriteBatch;

    private Entity player;
    private IEntityListener playerListener;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
        spriteBatch = new SpriteBatch();
        hud = new Hud(spriteBatch);
    }

    @Override
    public void process(GameData gameData) {
        spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(gameData.getDelta());
        hud.getStage().draw();

        hud.getStage().getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        if (player != null) {
            PlayerPart playerPart = player.getPart(PlayerPart.class);
            hud.setMoney(playerPart.getBalance());
        }
    }

    @Override
    public void setHealth(int value) {
        hud.setHealth(value);
    }

    @Override
    public void setWantedLevel(int value) {
        hud.setWantedLevel(value);
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
