package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEntityManager;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IFamilyManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class FamilyManager implements IFamilyManager {

    private Map<IFamily, List<Entity>> families;

    @Reference
    private IEntityManager entityManager;

    public FamilyManager() {
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

}
