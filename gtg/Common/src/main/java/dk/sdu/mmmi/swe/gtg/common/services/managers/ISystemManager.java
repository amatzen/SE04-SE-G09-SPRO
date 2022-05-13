package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;

public interface ISystemManager {
    void addEntityProcessingService(IProcessingSystem service);

    void removeEntityProcessingService(IProcessingSystem service);

    void addPostEntityProcessingService(IPostProcessingSystem service);

    void removePostEntityProcessingService(IPostProcessingSystem service);

    void update(GameData gameData);

    void reset();
}
