package dk.sdu.mmmi.swe.gtg.shapefactory.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;

public class ShapeFactory implements ShapeFactorySPI {

    private IWorldManager worldManager;

    @Override
    public Body createRectangle(Vector2 position, Vector2 size, BodyDef.BodyType bodyType, float density, boolean sensor) {

        final BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = bodyType;
        final Body body = worldManager.createBody(bodyDef);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.isSensor = sensor;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    @Override
    public Body createEllipse() {
        return null;
    }

    public void setWorldManager(IWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public void removeWorldManager() {
        this.worldManager = null;
    }
}
