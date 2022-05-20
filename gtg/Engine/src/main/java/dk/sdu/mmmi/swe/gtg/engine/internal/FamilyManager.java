package dk.sdu.mmmi.swe.gtg.engine.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IFamilyManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class FamilyManager implements IFamilyManager {

    private final Map<IFamily, List<Entity>> families;
    private final Map<IFamily, List<IEntityListener>> listeners;

    @Reference
    private IEntityManager entityManager;

    public FamilyManager() {
        listeners = new ConcurrentHashMap<>();
        families = new ConcurrentHashMap<>();
    }

    @Override
    public void updateFamilyMembership(Entity entity) {
        updateFamilyMembership(entity, false);
    }

    @Override
    public void updateFamilyMembership(Entity entity, boolean remove) {
        for (IFamily family : families.keySet()) {
            final List<Entity> familyEntities = families.get(family);

            boolean isMember = entity.isMember(family);
            boolean matches = family.matches(entity) && !remove;

            if (isMember != matches) {
                if (matches) {
                    entity.addFamily(family);
                    familyEntities.add(entity);
                } else {
                    entity.removeFromFamily(family);
                    familyEntities.remove(entity);
                }
                notifyListeners(family, entity, !matches);
            }
        }
    }

    private void notifyListeners(IFamily family, Entity entity, boolean remove) {
        List<IEntityListener> listeners = this.listeners.get(family);

        if (listeners == null) {
            return;
        }

        for (IEntityListener listener : listeners) {
            if (family.matches(entity)) {
                if (remove) {
                    listener.onEntityRemoved(entity);
                } else {
                    listener.onEntityAdded(entity);
                }
            }
        }
    }

    @Override
    public List<Entity> getEntitiesFor(IFamily family) {
        return registerFamily(family);
    }

    @Override
    public List<Entity> registerFamily(IFamily family) {
        List<Entity> entities = families.get(family);

        if (entities == null) {
            entities = new CopyOnWriteArrayList<>();
            families.put(family, entities);

            entityManager.getEntities().forEach(entity -> {
                updateFamilyMembership(entity);
            });
        }

        return Collections.unmodifiableList(entities);
    }

    @Override
    public void addEntityListener(IFamily family, IEntityListener listener) {
        List<IEntityListener> listeners = this.listeners.get(family);

        registerFamily(family);

        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<>();
        }

        listeners.add(listener);

        this.listeners.put(family, listeners);
    }

    @Override
    public void removeEntityListener(IFamily family, IEntityListener listener) {
        List<IEntityListener> listeners = this.listeners.get(family);

        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void reset() {
        this.families.clear();
        this.listeners.clear();
    }
}
