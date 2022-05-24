package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BoidSeparationPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class BoidSeparationSystem implements IProcessingSystem {

    private List<? extends Entity> entities;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
        entities = engine.getEntitiesFor(
                Family.builder().with(BoidSeparationPart.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        for (Entity entity : entities) {
            BoidSeparationPart separationPart = entity.getPart(BoidSeparationPart.class);

            Vector2 steering = separate(
                    entity,
                    engine.getEntitiesFor(separationPart.getBoidFamily()),
                    separationPart.getCriticalDistance()
            );

            entity.getPart(BodyPart.class).getBody().applyForceToCenter(steering, true);
        }
    }

    private Vector2 separate(Entity entity, List<? extends Entity> boids, float separationDistance) {
        Body body = entity.getPart(BodyPart.class).getBody();

        Vector2 steering = new Vector2(0f, 0f);

        int count = 0;

        for (Entity boid : boids) {
            if (boid == entity) {
                continue;
            }

            Body otherBody = boid.getPart(BodyPart.class).getBody();

            Vector2 toOther = body.getPosition().cpy().sub(otherBody.getPosition());

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
            steering.scl(body.getMass() * 50);
            steering.limit(50000f);
        }

        return steering;
    }
}
