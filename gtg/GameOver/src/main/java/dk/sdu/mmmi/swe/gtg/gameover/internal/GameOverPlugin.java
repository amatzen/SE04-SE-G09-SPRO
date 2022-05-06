package dk.sdu.mmmi.swe.gtg.gameover.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import org.osgi.service.component.annotations.Component;

@Component
public class GameOverPlugin implements IPlugin {

    @Override
    public void install(IEngine engine, GameData gameData) {

    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {

    }
}
