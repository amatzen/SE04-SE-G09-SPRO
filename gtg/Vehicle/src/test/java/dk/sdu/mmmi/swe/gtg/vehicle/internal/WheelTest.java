package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WheelTest {

    @Test
    public void wheelPowerTest() {
        Wheel wheel = new Wheel(false);
        Assertions.assertFalse(wheel.isPowered());

        Wheel wheelPowered = new Wheel(true);
        Assertions.assertTrue(wheelPowered.isPowered());
    }

    @Test
    public void wheelTurnTest() {
        VehiclePlugin vp = new VehiclePlugin();
        VehicleControlSystem vcs = new VehicleControlSystem();

        World world = new World(new Vector2(0, 0), true);

        /*
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
         */

        Wheel wheel = new Wheel(true);
        BodyDef wheelBodyDef = new BodyDef();
        wheelBodyDef.position.set(new Vector2(20f, 40f));
        wheelBodyDef.type = BodyDef.BodyType.DynamicBody;
        Body wheelBody = world.createBody(wheelBodyDef);
        BodyPart wheelBodyPart = new BodyPart(wheelBody);
        wheel.addPart(wheelBodyPart);

        DriveTrain dt = new DriveTrain(wheel);
        BodyDef vehicleBodyDef = new BodyDef();
        vehicleBodyDef.position.set(new Vector2(134.28f, 79.85f));
        vehicleBodyDef.type = BodyDef.BodyType.DynamicBody;
        Body vehicleBody = world.createBody(vehicleBodyDef);
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(1.7f * 0.5f, 4.0f * 0.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 260f;
        fixtureDef.isSensor = false;
        vehicleBody.createFixture(fixtureDef);
        BodyPart vehicleBodyPart = new BodyPart(vehicleBody);
        Vehicle vehicle = new Vehicle();
        vehicle.addPart(vehicleBodyPart);
        vehicle.addPart(dt);

        vcs.turnWheel(0, wheel, vehicleBody);
        System.out.println(vehicleBody.getAngle());


    }

}
