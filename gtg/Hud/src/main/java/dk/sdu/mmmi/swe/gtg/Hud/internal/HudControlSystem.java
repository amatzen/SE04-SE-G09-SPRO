package dk.sdu.mmmi.swe.gtg.Hud.internal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

@Component
public class HudControlSystem implements IPostEntityProcessingService {

    private Hud hud;

    @Override
    public void addedToEngine(IEngine engine) {

    }

    @Override
    public void process(GameData gameData) {

        hud = new Hud(gameData.getSpriteBatch());

        gameData.getSpriteBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

}
