package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;

import java.util.List;

public interface IEngine {

    void update(GameData gameData);

    String addEntity(Entity entity);

    void removeEntity(String entityID);

    void removeEntity(Entity entity);

    Entity getEntity(String ID);

    List<Entity> getEntitiesFor(IFamily family);

    void addEntityProcessingService(IEntityProcessingService service);

    void removeEntityProcessingService(IEntityProcessingService service);

    void addPostEntityProcessingService(IPostEntityProcessingService service);

    void removePostEntityProcessingService(IPostEntityProcessingService service);
}
