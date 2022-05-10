package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class BoidSeparationSystem implements IProcessingSystem {
    @Override
    public void addedToEngine(IEngine engine) {

    }

    @Override
    public void process(GameData gameData) {

    }

    private Vector2 separate(Entity entity, List<? extends Entity> entities) {
        Body body = entity.getPart(BodyPart.class).getBody();

        float separationDistance = 2;

        Vector2 steering = Vector2.Zero;

        int count = 0;

        for (Entity e : entities) {
            if (e == entity) {
                continue;
            }

            Body otherBody = e.getPart(BodyPart.class).getBody();

            Vector2 toOther = otherBody.getPosition().cpy().sub(body.getPosition());

            float distance = toOther.len();

            if (distance > 0 && distance < separationDistance) {
                toOther.nor();
                toOther.scl(1 / distance);
                steering.add(toOther);

                count++;
            }
        }

        if (count > 0) {
            steering.scl(1 / count);
        }

        if (steering.len() > 0) {
            steering.nor();
            steering.scl(body.getMass() * 0.5f);
        }

        return steering;
    }
}
