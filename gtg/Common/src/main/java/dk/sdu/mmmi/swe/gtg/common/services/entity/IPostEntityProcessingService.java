package dk.sdu.mmmi.swe.gtg.common.services.entity;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {
        void processEntity(Entity entity, float deltaTime);
}
