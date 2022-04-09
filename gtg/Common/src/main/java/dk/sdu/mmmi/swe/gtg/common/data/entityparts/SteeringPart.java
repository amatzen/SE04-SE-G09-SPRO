package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;

public class SteeringPart implements IEntityPart {

    private Vector2 desiredVelocity;
    private Vector2 steering;

    @Override
    public void destroy() {

    }

    public void setDesiredVelocity(Vector2 desiredVelocity) {
        this.desiredVelocity = desiredVelocity;
    }

    public void setSteering(Vector2 steering) {
        this.steering = steering;
    }

    public Vector2 getDesiredVelocity() {
        return desiredVelocity;
    }

    public Vector2 getSteering() {
        return steering;
    }
}
