package dk.sdu.mmmi.swe.gtg.pathfindings.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;

public class PathfindingsPlugin implements IGamePluginService {
    public void start(IEngine engine, GameData gameData) {
        System.out.println("Pathfindings Module Loaded");

    }

    public void stop(IEngine engine, GameData gameData) {
        System.out.println("Pathfindings Module Unloaded");

    }
}
