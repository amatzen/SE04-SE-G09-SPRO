package dk.sdu.mmmi.swe.gtg.collision.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class CollisionPlugin implements IGamePluginService {

    @Reference
    private ShapeFactorySPI shapeFactory;

    private Collision collision;

    @Override
    public void start(IEngine engine, GameData gameData) {

        Vector2 position = new Vector2(5, 0);
        Vector2 size = new Vector2(1.0f, 1.0f);

        collision = new Collision();

        BodyPart body = new BodyPart(shapeFactory.createRectangle(
                position, size, BodyDef.BodyType.StaticBody,
                0.5f,
                false));

        collision.addPart(body);

        engine.addEntity(collision);

        System.out.println("CollisionPlugin started");

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.removeEntity(collision);
    }
}
