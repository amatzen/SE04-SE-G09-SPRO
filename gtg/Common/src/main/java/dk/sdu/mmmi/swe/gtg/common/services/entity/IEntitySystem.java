package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;

public interface IEntitySystem {

    void addedToEngine();

    void process(GameData gameData);

}
