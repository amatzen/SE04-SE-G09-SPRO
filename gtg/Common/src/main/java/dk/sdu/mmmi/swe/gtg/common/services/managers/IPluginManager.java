package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;

public interface IPluginManager {
    void update(GameData gameData);

    void uninstallAll(GameData gameData);

    void installAll(GameData gameData);
}
