package dk.sdu.mmmi.swe.gtg.impactdamagesystem.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ImpactDamageSystem implements IPlugin {

    @Reference
    private CollisionSPI collisionSPI;

    private ICollisionListener collisionListener;

    @Override
    public void install(IEngine engine, GameData gameData) {
        collisionListener = new ImpactDamageCollisionListener();
        collisionSPI.addListener(collisionListener);
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        collisionSPI.removeListener(collisionListener);
    }
}
