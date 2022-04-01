package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager implements IEntityManager {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    private final List<IEntityListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void addEntityListener(IEntityListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEntityListener(IEntityListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);

        listeners.forEach((listener) -> {
            listener.onEntityAdded(entity);
        });

        return entity.getID();
    }

    @Override
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());

        entity.getParts().forEach((part) -> {
            part.destroy();
        });

        listeners.forEach((listener) -> {
            listener.onEntityRemoved(entity);
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
}
