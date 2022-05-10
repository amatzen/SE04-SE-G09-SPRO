package dk.sdu.mmmi.swe.gtg.targetseekingsystem.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SeekingPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class TargetSeekingSystem implements IProcessingSystem {

    private List<? extends Entity> entities;

    @Override
    public void addedToEngine(IEngine engine) {
        entities = engine.getEntitiesFor(
            Family.builder().with(SeekingPart.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        for (Entity entity : entities) {
            SeekingPart seekingPart = entity.getPart(SeekingPart.class);

            if (seekingPart.getTarget() == null) {
                continue;
            }

            Vector2 force = seek(entity, seekingPart.getTarget());

            entity.getPart(BodyPart.class).getBody().applyForceToCenter(force, true);
        }
    }

    private Vector2 seek(Entity entity, Vector2 target) {
        Body entityBody = entity.getPart(BodyPart.class).getBody();

        Vector2 desired = target.cpy().sub(entityBody.getPosition());
        desired.nor();

        float tmpMaxSpeed = 17f;
        desired.scl(tmpMaxSpeed * entityBody.getMass());

        Vector2 steer = desired.sub(entityBody.getLinearVelocity());

        float tmpMaxForce = 10000f * 3f;
        steer.limit(tmpMaxForce);

        return steer;
    }
}
