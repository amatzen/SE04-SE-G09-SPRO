package dk.sdu.mmmi.swe.gtg.bodycomputationcontroller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface BodyComputationSPI {
    Vector2 getForwardVelocity(Body body);

    Vector2 getLateralVelocity(Body body);
}
