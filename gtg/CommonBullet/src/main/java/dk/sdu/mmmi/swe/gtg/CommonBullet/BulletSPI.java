package dk.sdu.mmmi.swe.gtg.CommonBullet;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;

/**
 * Public API representing an example OSGi service
 */
public interface BulletSPI
{
    // public methods go here...
    Entity createBullet(Entity vehicleEntity, GameData gameData); // might change to
}

