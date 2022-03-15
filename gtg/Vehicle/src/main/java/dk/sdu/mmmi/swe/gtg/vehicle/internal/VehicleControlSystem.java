package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.World;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;

public class VehicleControlSystem implements IEntityProcessingService {
    @Override
    public void afterProcessing(GameData gameData, World world) {

    }

    @Override
    public void beforeProcessing(GameData gameData, World world) {

    }

    @Override
    public Class<Vehicle> getEntityType() {
        return Vehicle.class;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {

    }
}
