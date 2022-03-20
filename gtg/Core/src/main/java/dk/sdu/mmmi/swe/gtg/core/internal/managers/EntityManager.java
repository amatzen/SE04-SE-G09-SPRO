package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager implements IEntityManager {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    private List<IEntityListener> listeners = new CopyOnWriteArrayList<>();

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
        return entity.getID();
    }

    @Override
    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    @Override
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    @Override
    public Entity getEntity(String ID) {
        return entityMap.get(ID);
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
