package dk.sdu.mmmi.swe.gtg.core.internal.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IFamilyManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FamilyManager implements IFamilyManager {

    private Map<Family, List<Entity>> families;

    public FamilyManager() {
        families = new ConcurrentHashMap<>();
    }

    @Override
    public void updateFamilyMembership(Entity entity) {
        updateFamilyMembership(entity, false);
    }

    @Override
    public void updateFamilyMembership(Entity entity, boolean remove) {
        for (Family family : families.keySet()) {
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
    public List<Entity> getEntitiesFor(Family family) {
        return registerFamily(family);
    }

    @Override
    public List<Entity> registerFamily(Family family) {
        List<Entity> entities = families.get(family);

        if (entities == null) {
            entities = new ArrayList<>();
            families.put(family, entities);
        }

        entities.forEach(entity -> {
            updateFamilyMembership(entity);
        });

        return Collections.unmodifiableList(entities);
    }

}
