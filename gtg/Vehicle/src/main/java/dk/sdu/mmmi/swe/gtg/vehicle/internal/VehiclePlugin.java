package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class VehiclePlugin implements IGamePluginService {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private Vehicle vehicle;

    private final Vector2 WHEEL_SIZE = new Vector2(0.32f, 0.64f);
    private final float WHEEL_OFFSET_X = 1.7f * 0.5f - WHEEL_SIZE.x * 0.45f;
    private final float WHEEL_OFFSET_Y = 4.0f * 0.3f;

    @Override
    public void start(IEngine engine, GameData gameData) {
        engine.getEntitiesFor(Family.builder().forEntities(Vehicle.class, Wheel.class).get());
        Vector2 position = new Vector2(0, 0);
        Vector2 size = new Vector2(1.7f, 4.0f);

        vehicle = new Vehicle();
        BodyPart vehicleBody = new BodyPart(
            shapeFactory.createRectangle(
                position,
                size,
                BodyDef.BodyType.DynamicBody,
                260f,
                false
            )
        );

        vehicleBody.getBody().setLinearDamping(0.15f);
        vehicleBody.getBody().getFixtureList().get(0).setRestitution(0.2f);

        vehicle.addPart(vehicleBody);
        vehicle.addPart(new TransformPart());

        final TexturePart texturePart = new TexturePart();


        //System.out.println(Gdx.files.internal("assets/taxi.png").exists());
        //System.out.println(Gdx.files.internal("assets/taxi.png").path());

        AssetManager assetManager = new AssetManager();
        
        FileHandle file = Gdx.files.internal("assets/taxi.png");
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);
        vehicle.addPart(texturePart);

        Wheel[] wheels = createWheels(vehicle);

        DriveTrain driveTrain = new DriveTrain(wheels);
        vehicle.addPart(driveTrain);

        for (Wheel wheel : wheels) {
            engine.addEntity(wheel);
        }
        engine.addEntity(vehicle);

        System.out.println("VehiclePlugin started");
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
    public void stop(IEngine engine, GameData gameData) {
        engine.getEntitiesFor(Family.builder().forEntities(Vehicle.class, Wheel.class).get()).forEach(entity -> {
            engine.removeEntity(entity);
        });
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
