package dk.sdu.mmmi.swe.gtg.collision.internal;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;

import java.util.List;

public class CarCollisionDetector implements IPostEntityProcessingService {

    private IEngine engine;
    private List<Vehicle> vehicleList;


    @Override
    public void addedToEngine(IEngine engine) {
        this.engine = engine;

        vehicleList = (List<Vehicle>) engine.getEntitiesFor(
                Family.builder().forEntities(Vehicle.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {

        for (Vehicle vehicle : vehicleList ) {
            for (Vehicle vehicle2 : vehicleList) {

                // If the two entities are identical, skip the iteration
                if (vehicle.getID().equals(vehicle2.getID())) {
                    continue;
                }

                // CollisionDetection logic
                if (checkCollision(vehicle, vehicle2)) {
                    System.out.println("Collision detected!");
                }
            }
        }
    }

    // A function that checks collisions
    public Boolean checkCollision(Vehicle entity, Vehicle entity2) {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);

        float dx = entMov.getX() - entMov2.getX();
        float dy = entMov.getY() - entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Calculates collision between two entities
        if (distance < (entity.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

}
