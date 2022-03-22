package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.EntityPart;

public interface IEntityListener {

    void onEntityAdded(Entity entity);

    void onEntityRemoved(Entity entity);

    void onEntityPartAdded(Entity entity, EntityPart entityPart);

    void onEntityPartRemoved(Entity entity, EntityPart entityPart);

}
