package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.physics.box2d.Body;

public class BodyPart implements IEntityPart {

    private Body body;

    private boolean isDestroyed = false;

    public BodyPart(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public void destroy() {
        if (isDestroyed) return;

        body.getWorld().destroyBody(body);
        isDestroyed = true;
    }
}