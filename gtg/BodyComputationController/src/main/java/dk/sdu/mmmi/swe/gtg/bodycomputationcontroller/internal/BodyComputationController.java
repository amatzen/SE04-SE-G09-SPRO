package dk.sdu.mmmi.swe.gtg.bodycomputationcontroller.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.bodycomputationcontroller.BodyComputationSPI;
import org.osgi.service.component.annotations.Component;

@Component
public class BodyComputationController implements BodyComputationSPI {

    @Override
    public Vector2 getForwardVelocity(Body body) {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return new Vector2(currentNormal).scl(dotProduct);
    }

    @Override
    public Vector2 getLateralVelocity(Body body) {
        final Vector2 currentNormal = body.getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(body.getLinearVelocity());
        return new Vector2(currentNormal).scl(dotProduct);
    }

}
