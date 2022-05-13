package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager implements IEntityManager {

    private final Map<String, Entity> entityMap;

    public EntityManager() {
        entityMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());

        entity.getParts().forEach((part) -> {
            part.destroy();
        });
    }

    @Override
    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    @Override
    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(entityMap.values());
    }

    @Override
    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> res = new ArrayList<>();

        getEntities().forEach((entity) -> {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(entity.getClass())) {
                    res.add(entity);
                }
            }
        });

        return res;
    }

    @Override
    public void reset() {

    }
}
