package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class VehiclePlugin implements IGamePluginService {

    hest

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private Vehicle vehicle;

    private final Vector2 WHEEL_SIZE = new Vector2(0.32f, 0.64f);
    private final float WHEEL_OFFSET_X = 1.28f;
    private final float WHEEL_OFFSET_Y = 1.6f;

    @Override
    public void start(IEngine engine, GameData gameData) {

        Vector2 position = new Vector2(0, 0);
        Vector2 size = new Vector2(1.28f, 2.56f);

        vehicle = new Vehicle();
        BodyPart body = new BodyPart(
            shapeFactory.createRectangle(
                position,
                size,
                BodyDef.BodyType.DynamicBody,
                0.4f,
                false
            )
        );

        body.getBody().setLinearDamping(0.5f);
        body.getBody().getFixtureList().get(0).setRestitution(0.2f);

        vehicle.addPart(body);

        createWheels(vehicle, 4);

        engine.addEntity(vehicle);

        System.out.println("VehiclePlugin started");
    }

    private void createWheels(Vehicle vehicle, final int wheelDrive) {
        BodyPart vehicleBody = vehicle.getPart(BodyPart.class);

        System.out.println(vehicleBody);
        for (int i = 0; i < 4; i++) {
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
                default:
                    throw new IllegalArgumentException("Wheel number not supported. Create logic for positioning wheel with number " + i);
            }

            final boolean powered = i < 2;

            final Wheel wheel = new Wheel(
                    shapeFactory.createRectangle(
                        new Vector2(
                            vehicleBody.getBody().getPosition().x + xOffset,
                            vehicleBody.getBody().getPosition().y + yOffset
                        ),
                        WHEEL_SIZE,
                        BodyDef.BodyType.DynamicBody,
                        0.4f,
                        true
                    ),
                    vehicle,
                    powered
            );

            if (i < 2) {
                final RevoluteJointDef jointDef = new RevoluteJointDef();
                jointDef.initialize(vehicleBody.getBody(), wheel.getBody(), wheel.getBody().getWorldCenter());
                jointDef.enableMotor = false;
                worldManager.createJoint(jointDef);
            } else {
                final PrismaticJointDef jointDef = new PrismaticJointDef();
                jointDef.initialize(vehicleBody.getBody(), wheel.getBody(), wheel.getBody().getWorldCenter(), new Vector2(1, 0));
                jointDef.enableLimit = true;
                jointDef.lowerTranslation = jointDef.upperTranslation = 0;
                worldManager.createJoint(jointDef);
            }

            vehicle.getAllWheels().add(wheel);
            if (i < 2) {
                vehicle.getRevolvingWheels().add(wheel);
            }

        }

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.removeEntity(vehicle);
    }

    public void setShapeFactory(ShapeFactorySPI shapeFactory) {
        this.shapeFactory = shapeFactory;
    }

    public void removeShapeFactory(ShapeFactorySPI shapeFactory) {
        this.shapeFactory = null;
    }

    public void setWorldManager(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public void removeWorldManager(IWorldManager worldManager) {
        this.worldManager = null;
    }
}
