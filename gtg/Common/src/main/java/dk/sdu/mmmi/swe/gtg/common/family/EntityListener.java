package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public abstract class EntityListener implements IEntityListener {
    @Override
    public void onEntityAdded(Entity entity) {

    }

    @Override
    public void onEntityPartAdded(Entity entity, IEntityPart entityPart) {

    }

    @Override
    public void onEntityPartRemoved(Entity entity, IEntityPart entityPart) {

    }

    @Override
    public void onEntityRemoved(Entity entity) {

    }
}
