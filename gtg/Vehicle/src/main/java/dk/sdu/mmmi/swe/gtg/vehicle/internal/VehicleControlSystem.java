package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.bodycomputationcontroller.BodyComputationSPI;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.GameKeys;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;
import dk.sdu.mmmi.swe.gtg.wantedlevelsystemcommon.services.IWantedLevelSystem;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class VehicleControlSystem implements IProcessingSystem {
    private final float REVERSE_POWER = 0.5f;
    private final float BREAK_POWER = 1.5f;
    private final float WHEEL_TURN_INCREMENT = 0.010f;
    private final float acceleration = 7200f;

    @Reference
    private BulletSPI bulletSPI;
    @Reference
    private BodyComputationSPI bcc;
    @Reference
    private IWantedLevelSystem wantedLevelSystem;

    private List<Vehicle> vehicleList;
    private Sound sound;

    @Reference
    private HudSPI hudSPI;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
        vehicleList = (List<Vehicle>) engine.getEntitiesFor(
                Family.builder().forEntities(Vehicle.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        for (Vehicle vehicle : vehicleList) {

            processInput(vehicle, gameData);

            DriveTrain driveTrain = vehicle.getPart(DriveTrain.class);

            for (Wheel wheel : driveTrain.getWheels()) {
                Body wheelBody = wheel.getPart(BodyPart.class).getBody();
                updateBody(wheelBody, vehicle);
            }
        }
    }

    private void processInput(Vehicle vehicle, GameData gameData) {
        final Vector2 baseVector = new Vector2(0, 0);
        Body vehicleBody = vehicle.getPart(BodyPart.class).getBody();
        DriveTrain driveTrain = vehicle.getPart(DriveTrain.class);

        if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
            shoot(vehicleBody);
        }

        if (gameData.getKeys().isPressed(GameKeys.P)) {
            wantedLevelSystem.reportCrime(10f);
        }

        if (gameData.getKeys().isPressed(GameKeys.H)) {
            LifePart lifePart = vehicle.getPart(LifePart.class);
            lifePart.setLife(100);
            hudSPI.setHealth(100);
        }
        if (gameData.getKeys().isPressed(GameKeys.G)) {
            LifePart lifePart = vehicle.getPart(LifePart.class);
            lifePart.setLife(1000);
            hudSPI.setHealth(1000);
        }

        float wheelAngle = driveTrain.getTurnAngle();

        if (gameData.getKeys().isDown(GameKeys.LEFT) || gameData.getKeys().isDown(GameKeys.A)) {
            if (wheelAngle < 0) {
                wheelAngle = 0;
            }
            wheelAngle += WHEEL_TURN_INCREMENT;
        } else if (gameData.getKeys().isDown(GameKeys.RIGHT) || gameData.getKeys().isDown(GameKeys.D)) {
            if (wheelAngle > 0) {
                wheelAngle = 0;
            }
            wheelAngle -= WHEEL_TURN_INCREMENT;
        } else {
            wheelAngle = 0;
        }

        driveTrain.setTurnAngle(wheelAngle);

        for (Wheel wheel : driveTrain.getWheels()) {
            turnWheel(wheelAngle, wheel, vehicleBody);
        }

        if (gameData.getKeys().isDown(GameKeys.UP) || gameData.getKeys().isDown(GameKeys.W)) {
            baseVector.set(0, acceleration);
        } else if (gameData.getKeys().isDown(GameKeys.DOWN) || gameData.getKeys().isDown(GameKeys.S)) {
            if (bcc.direction(vehicle.getPart(BodyPart.class).getBody()) == 0) {
                baseVector.set(0, -acceleration * REVERSE_POWER);
            } else if (bcc.direction(vehicleBody) == 1) {
                baseVector.set(0, -acceleration * BREAK_POWER);
            } else {
                baseVector.set(0, -acceleration * REVERSE_POWER);
            }
        }

        for (final Wheel wheel : driveTrain.getWheels()) {
            if (wheel.isPowered()) {
                Body wheelBody = wheel.getPart(BodyPart.class).getBody();
                wheelBody.applyForceToCenter(wheelBody.getWorldVector(baseVector), true);
            }
        }

        gameData.getCamera().position.x = vehicleBody.getPosition().x;
        gameData.getCamera().position.y = vehicleBody.getPosition().y;
        gameData.getCamera().update();
    }

    public void updateBody(Body wheelBody, Vehicle vehicle) {
        Vector2 lateralSpeed = bcc.getLateralVelocity(wheelBody);

        //body.setLinearVelocity(forwardSpeed);
        wheelBody.applyLinearImpulse(
                lateralSpeed.scl(-(wheelBody.getMass() + vehicle.getPart(BodyPart.class).getBody().getMass() / 4)),
                wheelBody.getWorldCenter(),
                true);
    }

    private void shoot(Body body) {
        Vector2 vehiclePosition = new Vector2(body.getPosition());
        Vector2 norm = body.getWorldVector(new Vector2(0, 1));
        norm.scl(2.5f);
        vehiclePosition.add(norm);

        Vector2 vehicleDirection = new Vector2(bcc.getForwardVelocity(body));

        Vector2 direction = body.getWorldVector(new Vector2(0, 1));
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Gunshot.mp3"));
        sound.play(0.3f);
        engine.addEntity(bulletSPI.createBullet(vehiclePosition, new Vector2(direction), vehicleDirection));

        wantedLevelSystem.reportCrime(1f);
    }

    protected void turnWheel(float angle, Wheel wheel, Body vehicleBody) {
        if (angle < 0) {
            angle = Math.max(angle, -wheel.getMaxAngle());
        } else {
            angle = Math.min(angle, wheel.getMaxAngle());
        }

        if (!wheel.getTurnDirection()) {
            angle *= -1;
        }

        Body wheelBody = wheel.getPart(BodyPart.class).getBody();
        wheelBody.setTransform(wheelBody.getPosition(), angle + vehicleBody.getAngle());
    }
}
