package dk.sdu.mmmi.swe.gtg.hud.internal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class HudControlSystem implements IPostProcessingSystem, HudSPI {
    private Hud hud;
    private SpriteBatch spriteBatch;

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

        PlayerPart player = engine.getEntitiesFor(Family.builder().with(PlayerPart.class).get()).get(0).getPart(PlayerPart.class);
        hud.setMoney(player.getBalance());
    }

    @Override
    public void setHealth(int value) {
        hud.setHealth(value);
    }

    @Override
    public void setWantedLevel(int value) {
        hud.setWantedLevel(value);
    }
}
