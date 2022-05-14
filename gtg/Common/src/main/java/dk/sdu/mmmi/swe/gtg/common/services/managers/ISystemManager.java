package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import jdk.nashorn.internal.ir.annotations.Reference;

public interface ISystemManager {
    void addProcessingSystem(IProcessingSystem service);

    void removeProcessingSystem(IProcessingSystem service);

    void addPostProcessingSystem(IPostProcessingSystem service);

    void removePostProcessingSystem(IPostProcessingSystem service);

    void update(GameData gameData);

    void reset();

    void addEntityProcessingService(IProcessingSystem service);

    void removeEntityProcessingService(IProcessingSystem service);

    void addPostEntityProcessingService(IPostProcessingSystem service);

    void removePostEntityProcessingService(IPostProcessingSystem service);
}
