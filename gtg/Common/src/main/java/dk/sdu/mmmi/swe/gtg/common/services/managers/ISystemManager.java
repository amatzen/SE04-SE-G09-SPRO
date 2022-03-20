package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;

public interface ISystemManager {
    void addEntityProcessingService(IEntityProcessingService service);

    void removeEntityProcessingService(IEntityProcessingService service);

    void addPostEntityProcessingService(IPostEntityProcessingService service);

    void removePostEntityProcessingService(IPostEntityProcessingService service);

    void update(GameData gameData);
}
