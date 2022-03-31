package dk.sdu.mmmi.swe.gtg.collision.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.collision.Collision;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class CollisionPlugin implements IGamePluginService {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private Collision collision;

    @Override
    public void start(IEngine engine, GameData gameData) {

        Vector2 position1 = new Vector2(10, 0);
        Vector2 size1 = new Vector2(1, 1);
        Vector2 position2 = new Vector2(10, 0);
        float radius = 10;

        BodyPart body1 = new BodyPart(shapeFactory.createRectangle(
                position1, size1, BodyDef.BodyType.StaticBody,
                1,
                false));

        BodyPart body2 = new BodyPart(shapeFactory.createCircle(
                position2, radius, BodyDef.BodyType.StaticBody,
                1,
                true));

        collision = new Collision();

        collision.addPart(body1);
        collision.addPart(body2);

        engine.addEntity(collision);

        worldManager.setContactLister(new ContactListener());

        body2.getBody().setUserData(collision);

    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.removeEntity(collision);
    }
}
