package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.World;

public interface IEntityBeforeProcessing {
    void beforeProcessing(GameData gameData, World world);
}
