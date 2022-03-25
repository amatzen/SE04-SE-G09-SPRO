package dk.sdu.mmmi.swe.gtg.vehicle.internal;

import com.badlogic.gdx.physics.box2d.Body;

public class Wheel {

    private Body body;
    private boolean powered;
    private boolean turnDirection;

    public Wheel(Body body, boolean powered, boolean turnDirection) {
        this.body = body;
        this.powered = powered;
    }

    public Body getBody() {
        return body;
    }

    public void setAngle(float angle) {
        if (!turnDirection) {
            angle *= -1;
        }

        body.setTransform(body.getPosition(), angle);
    }

    public boolean isPowered() {
        return powered;
    }
}
