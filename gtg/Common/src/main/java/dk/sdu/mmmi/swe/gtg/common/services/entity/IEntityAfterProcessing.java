package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.World;

public interface IEntityAfterProcessing {
    void afterProcessing(GameData gameData, World world);
}
