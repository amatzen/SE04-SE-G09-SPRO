package dk.sdu.mmmi.swe.gtg.collision;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

public class CollisionEntity {

    private boolean sensorPart;

    private Entity entity;

    public CollisionEntity(Entity entity, boolean sensorPart) {
        this.entity = entity;
        this.sensorPart = sensorPart;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isSensorPart() {
        return sensorPart;
    }
}
