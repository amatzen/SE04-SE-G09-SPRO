package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

import java.util.List;

public class VehicleControlSystem implements IEntityProcessingService {

    private IEngine engine;

    private List<Entity> vehicleList;

    private double counter = 0;

    @Override
    public void addedToEngine(IEngine engine) {
        this.engine = engine;

        vehicleList = engine.getEntitiesFor(
                Family.builder().forEntities(Vehicle.class).get()
        );

    }

    @Override
    public void process(GameData gameData) {
        counter += gameData.getDelta();

        if (counter > 1) {
            System.out.println(vehicleList);

            engine.addEntity(new Vehicle());
        }

        counter %= 1;
    }
}
