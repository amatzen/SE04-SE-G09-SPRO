package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;

import java.util.List;

public class VehicleControlSystem implements IEntityProcessingService {

    private IEngine engine;

    private List<Vehicle> vehicleList;

    private float counter = 0;

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

        }

    }

    private float wheelAngle = 0;
    private final float WHEEL_TURN_INCREMENT = 0.017f;
    private final float MAX_WHEEL_ANGLE = 0.35f;
    private float acceleration = 10f;

    private void processInput(Vehicle vehicle, GameData gameData) {
        final Vector2 baseVector = new Vector2(0, 0);

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
            wheel.setAngle(wheelAngle);
        }

        if (gameData.getKeys().isDown(GameKeys.UP)) {
            baseVector.set(0, acceleration);
        } else if (gameData.getKeys().isDown(GameKeys.DOWN)) {
            /*if (direction() == DIRECTION_BACKWARD) {
                baseVector.set(0, -acceleration * REVERSE_POWER);
            } else if (direction() == DIRECTION_FORWARD) {
                baseVector.set(0, -acceleration * BREAK_POWER);
            } else {
                baseVector.set(0, -acceleration);
            }*/
        }
        // we currently set mCurrentMaxSpeed to regular speed, but we can use this to increase max
        // speed if user has turbo, or something like that. So we can apply this logic:
        // if (turboActive) {
        //    mCurrentMaxSpeed = mRegularMaxSpeed * 1.5f;
        //}
        /*mCurrentMaxSpeed = mRegularMaxSpeed;

        if (getBody().getLinearVelocity().len() < mCurrentMaxSpeed) {
            for (final Wheel wheel : new Array.ArrayIterator<Wheel>(mAllWheels)) {
                if (wheel.isPowered()) {
                    wheel.getBody().applyForceToCenter(wheel.getBody().getWorldVector(baseVector), true);
                }
            }
        }*/
    }
}
