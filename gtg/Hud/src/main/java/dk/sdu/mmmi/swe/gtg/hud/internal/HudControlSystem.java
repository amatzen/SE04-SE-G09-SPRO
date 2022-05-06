package dk.sdu.mmmi.swe.gtg.hud.internal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import org.osgi.service.component.annotations.Component;

@Component
public class HudControlSystem implements IPostProcessingSystem, HudSPI {

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


    @Override
    public void setHealth(int value) {
        hud.setHealth(value);
    }

    @Override
    public int getHealth() {
    return hud.getHealth();
    }

    @Override
    public void loseHealth(int value) {
    hud.loseHealth(value);
    }

    @Override
    public void addMoney(int value) {
    hud.addMoney(value);
    }

    @Override
    public void removeMoney(int value) {
    hud.removeMoney(value);
    }

    @Override
    public int getMoney() {
    return hud.getHealth();
    }

    @Override
    public void addWanted(int value) {
        hud.addWanted(value);
    }

    @Override
    public void decreaseWanted(int value) {
    hud.decreaseWanted(value);
    }

    @Override
    public int getWanted() {
        return hud.getWanted();

    }


}
