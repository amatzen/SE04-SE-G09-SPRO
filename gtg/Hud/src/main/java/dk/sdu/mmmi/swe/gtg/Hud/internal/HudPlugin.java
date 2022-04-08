package dk.sdu.mmmi.swe.gtg.Hud.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import org.osgi.service.component.annotations.Component;

@Component
public class HudPlugin implements IGamePluginService {

    @Override
    public void start(IEngine engine, GameData gameData) {

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {

    }
}
