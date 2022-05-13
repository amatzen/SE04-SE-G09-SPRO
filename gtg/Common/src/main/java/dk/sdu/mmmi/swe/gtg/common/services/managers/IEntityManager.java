package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

import java.util.Collection;
import java.util.List;

public interface IEntityManager {
    void addEntity(Entity entity);

    void removeEntity(Entity entity);

    Entity getEntity(String ID);

    Collection<Entity> getEntities();

    <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes);
}
