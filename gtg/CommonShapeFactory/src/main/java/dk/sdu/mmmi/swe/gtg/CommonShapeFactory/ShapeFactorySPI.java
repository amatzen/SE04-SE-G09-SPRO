package dk.sdu.mmmi.swe.gtg.CommonShapeFactory;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Public API representing an example OSGi service
 */
public interface ShapeFactorySPI {
    Body createRectangle();
    Body createEllipse();
}
