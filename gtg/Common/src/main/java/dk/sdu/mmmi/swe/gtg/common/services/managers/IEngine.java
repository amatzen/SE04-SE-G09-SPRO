package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;

import java.util.List;

public interface IEngine {

    void update(GameData gameData);

    void addEntity(Entity entity);

    void removeEntity(String entityID);

    void removeEntity(Entity entity);

    Entity getEntity(String ID);

    List<? extends Entity> getEntitiesFor(IFamily family);

    <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes);

    void addEntityProcessingService(IProcessingSystem service);

    void removeEntityProcessingService(IProcessingSystem service);

    void addPostEntityProcessingService(IPostProcessingSystem service);

    void removePostEntityProcessingService(IPostProcessingSystem service);
}
