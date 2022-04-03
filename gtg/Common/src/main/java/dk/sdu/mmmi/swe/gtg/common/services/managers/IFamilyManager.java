package dk.sdu.mmmi.swe.gtg.common.services.managers;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;

import java.util.List;

public interface IFamilyManager {
    void updateFamilyMembership(Entity entity);

    void updateFamilyMembership(Entity entity, boolean remove);

    List<Entity> getEntitiesFor(IFamily family);

    List<Entity> registerFamily(IFamily family);
}
