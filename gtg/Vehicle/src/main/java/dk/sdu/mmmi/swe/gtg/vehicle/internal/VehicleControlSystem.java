package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class VehicleControlSystem implements IEntityProcessingService {

    private final float DRIFT_OFFSET = 1.0f;
    private IEngine engine;

    private List<Vehicle> vehicleList;
    private final float REVERSE_POWER = 0.5f;
    private final float BREAK_POWER = 1.3f;
    private final float MAX_SPEED = 35.5f;
    private float drift = 0.5f;

    private float wheelAngle = 0;
    private final float WHEEL_TURN_INCREMENT = 0.017f;
    private final float MAX_WHEEL_ANGLE = 0.35f;
    private float acceleration = 80f;

    @Override
    public void addedToEngine(IEngine engine) {
        this.engine = engine;

        vehicleList = (List<Vehicle>) engine.getEntitiesFor(
                Family.builder().forEntities(Vehicle.class).get()
        );

    }

    @Override
    public void process(GameData gameData) {

        for (Vehicle vehicle : vehicleList) {

            processInput(vehicle, gameData);

            for (Wheel wheel : vehicle.getAllWheels()) {
                updateBody(wheel.getBody(), gameData.getDelta());
            }

        }

    }

    private void processInput(Vehicle vehicle, GameData gameData) {
        final Vector2 baseVector = new Vector2(0, 0);
        Body vehicleBody = vehicle.getPart(BodyPart.class).getBody();

        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            if (wheelAngle < 0) {
                wheelAngle = 0;
            }
            wheelAngle = Math.min(wheelAngle += WHEEL_TURN_INCREMENT, MAX_WHEEL_ANGLE);
        } else if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            if (wheelAngle > 0) {
                wheelAngle = 0;
            }
            wheelAngle = Math.max(wheelAngle -= WHEEL_TURN_INCREMENT, -MAX_WHEEL_ANGLE);
        } else {
            wheelAngle = 0;
        }

        for (final Wheel wheel : vehicle.getRevolvingWheels()) {
            wheel.setAngle(vehicleBody.getAngle() + wheelAngle);
        }

        if (gameData.getKeys().isDown(GameKeys.UP)) {
            baseVector.set(0, acceleration);
        } else if (gameData.getKeys().isDown(GameKeys.DOWN)) {
            if (direction(vehicle.getPart(BodyPart.class).getBody()) == 0) {
                baseVector.set(0, -acceleration * REVERSE_POWER);
            } else if (direction(vehicleBody) == 1) {
                baseVector.set(0, -acceleration * BREAK_POWER);
            } else {
                baseVector.set(0, -acceleration);
            }
        }

        if (vehicle.getPart(BodyPart.class).getBody().getLinearVelocity().len() < MAX_SPEED) {
            for (final Wheel wheel : vehicle.getAllWheels()) {
                if (wheel.isPowered()) {
                    wheel.getBody().applyForceToCenter(wheel.getBody().getWorldVector(baseVector), true);
                }
            }
        }
    }

    public int direction(Body body) {
        final float tolerance = 0.2f;
        if (body.getLinearVelocity().y < -tolerance) {
            return 0;
        } else if (body.getLinearVelocity().y > tolerance) {
            return 1;
        } else {
            return 2;
        }
    }

    public void updateBody(Body body, final float delta) {
        if (drift < 1) {
            Vector2 forwardSpeed = getForwardVelocity(body);
            Vector2 lateralSpeed = getLateralVelocity(body);

            body.setLinearVelocity(forwardSpeed);
        }
    }

    public Vector2 getForwardVelocity(Body body) {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return new Vector2(currentNormal).scl(dotProduct);
    }

    public Vector2 getLateralVelocity(Body body) {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return new Vector2(currentNormal).scl(dotProduct);
    }

}
