package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;

import java.util.Collection;
import java.util.List;

public interface IEntityManager {
    void addEntityListener(IEntityListener listener);

    void removeEntityListener(IEntityListener listener);

    String addEntity(Entity entity);

    void removeEntity(Entity entity);

    Entity getEntity(String ID);

    Collection<Entity> getEntities();

    <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes);
}
