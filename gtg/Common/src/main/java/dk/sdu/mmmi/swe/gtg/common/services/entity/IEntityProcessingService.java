package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

public interface IEntityProcessingService<T extends Entity> extends IEntityBeforeProcessing, IEntityAfterProcessing {
    void processEntity(T entity, float deltaTime);
}
