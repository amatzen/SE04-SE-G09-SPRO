package dk.sdu.mmmi.swe.gtg.common.data;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public class EntityPartPair {
    private final Entity entity;
    private final IEntityPart part;

    public EntityPartPair(Entity entity, IEntityPart part) {
        this.entity = entity;
        this.part = part;
    }

    public Entity getEntity() {
        return entity;
    }

    public IEntityPart getPart() {
        return part;
    }
}
