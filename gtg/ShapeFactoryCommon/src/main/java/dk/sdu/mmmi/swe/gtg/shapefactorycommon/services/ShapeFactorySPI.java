package dk.sdu.mmmi.swe.gtg.shapefactorycommon.services;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Public API representing an example OSGi service
 */
public interface ShapeFactorySPI {
    Body createRectangle(final Vector2 position, final Vector2 size, final BodyDef.BodyType bodyType, float density, final boolean sensor);

    Body createEllipse();
}

