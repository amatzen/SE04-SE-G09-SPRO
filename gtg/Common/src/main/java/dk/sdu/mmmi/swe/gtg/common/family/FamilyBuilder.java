package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FamilyBuilder implements IFamilyBuilder {

    private final Family family;

    public FamilyBuilder() {
        family = new Family();
    }

    @Override
    public IFamilyBuilder forEntities(Class<? extends Entity> ... entities) {
        Set<Class<? extends Entity>> entitySet = ConcurrentHashMap.newKeySet();
        entitySet.addAll(Arrays.asList(entities));
        family.setEntities(entitySet);
        return this;
    }

    @Override
    public IFamilyBuilder with(Class<? extends IEntityPart> ... parts) {
        Set<Class<? extends IEntityPart>> partSet = ConcurrentHashMap.newKeySet();
        partSet.addAll(Arrays.asList(parts));
        family.setParts(partSet);
        return this;
    }

    @Override
    public Family get() {
        return family;
    }
}
