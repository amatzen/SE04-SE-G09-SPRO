package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.physics.box2d.Body;

public class Wheel {

    private Body body;
    private Vehicle vehicle;
    private boolean powered;

    public Wheel(Body body, Vehicle vehicle, boolean powered) {
        this.body = body;
        this.vehicle = vehicle;
        this.powered = powered;
    }

    public Body getBody() {
        return body;
    }

    public void setAngle(float angle) {
        body.setTransform(body.getPosition(), angle);
    }
}
