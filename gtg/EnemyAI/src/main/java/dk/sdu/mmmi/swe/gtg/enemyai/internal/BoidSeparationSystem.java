package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class BoidSeparationSystem implements IProcessingSystem {

    private List<? extends Entity> enemies;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
        enemies = engine.getEntitiesFor(
                Family.builder().forEntities(Enemy.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        for (Entity e : enemies) {
            Vector2 steering = separate(e, enemies).scl(-100f);
            e.getPart(BodyPart.class).getBody().applyForceToCenter(steering, true);
        }
    }

    private Vector2 separate(Entity entity, List<? extends Entity> entities) {
        Body body = entity.getPart(BodyPart.class).getBody();

        float separationDistance = 5f;

        Vector2 steering = new Vector2(0f, 0f);

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
            steering.scl(body.getMass());
            //steering.limit(1000f);
        }

        return steering;
    }
}
