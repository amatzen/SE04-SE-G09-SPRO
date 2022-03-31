package dk.sdu.mmmi.swe.gtg.common.family;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public interface IEntityListener {

    void onEntityAdded(Entity entity);

    void onEntityRemoved(Entity entity);

    void onEntityPartAdded(Entity entity, IEntityPart entityPart);

    void onEntityPartRemoved(Entity entity, IEntityPart entityPart);

}
