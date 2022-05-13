package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonhud.HudSPI;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class VehiclePlugin implements IPlugin {

    private final Vector2 WHEEL_SIZE = new Vector2(0.32f, 0.64f);
    private final float WHEEL_OFFSET_X = 1.7f * 0.5f - WHEEL_SIZE.x * 0.40f;
    private final float WHEEL_OFFSET_Y = 4.0f * 0.3f;

    @Reference
    private HudSPI hudSPI;

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private IEntityListener vehicleListener;

    @Override
    public void install(IEngine engine, GameData gameData) {
        vehicleListener = new EntityListener() {
            @Override
            public void onEntityRemoved(Entity entity) {
                DriveTrain driveTrain = entity.getPart(DriveTrain.class);
                if (driveTrain != null) {
                    for (Wheel wheel : driveTrain.getWheels()) {
                        engine.removeEntity(wheel);
                    }
                }
            }
        };

        engine.addEntityListener(
                Family.builder().forEntities(Vehicle.class).get(),
                vehicleListener
        );

        Vehicle vehicle = createVehicle(engine);

        vehicle.addPart(new PlayerPart());

        LifePart lifePart = new LifePart();
        lifePart.onDamage.add((signal, damage) -> {
            hudSPI.setHealth(lifePart.getLife());
        });

        vehicle.addPart(lifePart);

        vehicle.addPart(new WantedPart(0));

        engine.addEntity(vehicle);
    }

    public Vehicle createVehicle(IEngine engine) {
        Vehicle vehicle = createVehicleBody(
                new Vector2(134.28f, 79.85f), new Vector2(1.7f, 4.0f),
                0.15f, 0.2f, 260f
        );

        vehicle.addPart(getBodyTexture());

        Wheel[] wheels = createWheels(vehicle);

        DriveTrain driveTrain = createDriveTrain(wheels, engine);
        vehicle.addPart(driveTrain);

        return vehicle;
    }

    private DriveTrain createDriveTrain(Wheel[] wheels, IEngine engine) {
        DriveTrain driveTrain = new DriveTrain(wheels);

        for (Wheel wheel : wheels) {
            engine.addEntity(wheel);
        }

        return driveTrain;
    }

    private Vehicle createVehicleBody(final Vector2 position, final Vector2 size, final float drag,
                                      final float restitution, final float density) {
        Vehicle vehicle = new Vehicle();
        BodyPart vehicleBody = new BodyPart(
                shapeFactory.createRectangle(
                        position,
                        size,
                        BodyDef.BodyType.DynamicBody,
                        density,
                        false
                )
        );

        vehicleBody.getBody().setUserData(vehicle);

        vehicleBody.getBody().setLinearDamping(drag);
        vehicleBody.getBody().getFixtureList().get(0).setRestitution(restitution);

        vehicle.addPart(vehicleBody);
        TransformPart transformPart = new TransformPart();
        transformPart.setScale(1f / 56f, 1f / 56f);
        transformPart.setZ(1);
        vehicle.addPart(transformPart);

        vehicleBody.getBody().setUserData(vehicle);

        return vehicle;
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

    private TexturePart getBodyTexture() {
        return getTexture("assets/entities/player.png");
    }

    private TexturePart getWheelTexture() {
        return getTexture("assets/entities/tire.png");
    }

    private Wheel[] createWheels(Vehicle vehicle) {
        Wheel[] wheels = new Wheel[4];

        BodyPart vehicleBody = vehicle.getPart(BodyPart.class);

        for (int i = 0; i < wheels.length; i++) {
            float xOffset = 0;
            float yOffset = 0;

            switch (i) {
                case 0:
                    xOffset = -WHEEL_OFFSET_X;
                    yOffset = WHEEL_OFFSET_Y;
                    break;
                case 1:
                    xOffset = WHEEL_OFFSET_X;
                    yOffset = WHEEL_OFFSET_Y;
                    break;
                case 2:
                    xOffset = -WHEEL_OFFSET_X;
                    yOffset = -WHEEL_OFFSET_Y;
                    break;
                case 3:
                    xOffset = WHEEL_OFFSET_X;
                    yOffset = -WHEEL_OFFSET_Y;
                    break;
            }

            final boolean powered = i < 2;
            final float maxTurningAngle = i < 2 ? 0.50f : 0.0f;

            final Wheel wheel = new Wheel(
                    powered,
                    maxTurningAngle,
                    true
            );

            BodyPart wheelBody = new BodyPart(shapeFactory.createRectangle(
                    new Vector2(
                            vehicleBody.getBody().getPosition().x + xOffset,
                            vehicleBody.getBody().getPosition().y + yOffset
                    ),
                    WHEEL_SIZE,
                    BodyDef.BodyType.DynamicBody,
                    60f,
                    true
            ));

            TransformPart wheelTransform = new TransformPart();
            wheelTransform.setScale(0.04f / 56f, 0.04f / 56f);

            wheel.addPart(wheelTransform);
            wheel.addPart(getWheelTexture());

            wheel.addPart(wheelBody);

            if (i < 2) {
                final RevoluteJointDef jointDef = new RevoluteJointDef();
                jointDef.initialize(vehicleBody.getBody(), wheelBody.getBody(), wheelBody.getBody().getWorldCenter());
                jointDef.enableMotor = false;
                worldManager.createJoint(jointDef);
            } else {
                final PrismaticJointDef jointDef = new PrismaticJointDef();
                jointDef.initialize(vehicleBody.getBody(), wheelBody.getBody(), wheelBody.getBody().getWorldCenter(), new Vector2(1, 0));
                jointDef.enableLimit = true;
                jointDef.lowerTranslation = jointDef.upperTranslation = 0;
                worldManager.createJoint(jointDef);
            }

            wheels[i] = wheel;
        }

        return wheels;
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(
            Family.builder().forEntities(Vehicle.class).get(),
            vehicleListener
        );

        engine.getEntitiesFor(Family.builder().forEntities(Vehicle.class, Wheel.class).get()).forEach(entity -> {
            engine.removeEntity(entity);
        });
    }
}
