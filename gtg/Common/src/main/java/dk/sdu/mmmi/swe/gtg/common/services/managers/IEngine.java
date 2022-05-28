package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;

import java.util.List;

public interface IEngine {

    void update(GameData gameData);

    void addEntity(Entity entity);

    void removeEntity(String entityID);

    void removeEntity(Entity entity);

    Entity getEntity(String ID);

    List<? extends Entity> getEntitiesFor(IFamily family);

    <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes);

    void addEntityListener(IFamily family, IEntityListener listener);

    void addEntityListener(IFamily family, IEntityListener listener, boolean iterate);

    void removeEntityListener(IFamily family, IEntityListener listener);

    void reset();
}
