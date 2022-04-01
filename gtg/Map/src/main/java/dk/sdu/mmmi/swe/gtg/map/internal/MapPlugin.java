package dk.sdu.mmmi.swe.gtg.map.internal;

import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import org.osgi.service.component.annotations.Component;

@Component
public class MapPlugin implements IGamePluginService {
    @Override
    public void start(IEngine engine, GameData gameData) {

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.getEntitiesFor(Family.builder().forEntities(Wall.class).get()).forEach(entity -> {
            engine.removeEntity(entity);
        });

    }
}
