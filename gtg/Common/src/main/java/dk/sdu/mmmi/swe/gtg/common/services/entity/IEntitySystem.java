package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

public interface IEntitySystem {

    void addedToEngine(IEngine engine);

    void process(GameData gameData);

}
