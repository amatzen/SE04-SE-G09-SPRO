package dk.sdu.mmmi.swe.gtg.hud.internal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

@Component
public class HudControlSystem implements IPostEntityProcessingService {

    private Hud hud;
    private SpriteBatch spriteBatch;

    @Override
    public void addedToEngine(IEngine engine) {
        spriteBatch = new SpriteBatch();
        hud = new Hud(spriteBatch);
    }

    @Override
    public void process(GameData gameData) {
        spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(gameData.getDelta());
        hud.getStage().draw();

        hud.getStage().getViewport().update(gameData.getDisplayWidth(), gameData.getDisplayHeight());
    }
}
