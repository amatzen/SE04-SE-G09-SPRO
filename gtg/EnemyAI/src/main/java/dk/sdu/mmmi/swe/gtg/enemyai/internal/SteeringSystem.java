package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SteeringPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.PathPart;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class SteeringSystem implements IProcessingSystem {

    private List<? extends Entity> entities;

    @Override
    public void addedToEngine(IEngine engine) {

        entities = engine.getEntitiesFor(
                Family.builder().with(BodyPart.class, PathPart.class).get()
        );

    }

    @Override
    public void process(GameData gameData) {

        followPath(gameData);

    }

    private void followPath(GameData gameData) {
        for (Entity entity : entities) {
            System.out.println("Following path");
            Vector2 f = followPath(entity);

            f.scl(3f);

            entity.getPart(BodyPart.class).getBody().applyForceToCenter(f, true);
        }
    }

    private Vector2 followPath(Entity entity) {
        PathPart pathPart = entity.getPart(PathPart.class);
        SteeringPart steeringPart = entity.getPart(SteeringPart.class);
        Body body = entity.getPart(BodyPart.class).getBody();

        Vector2 predict = body.getLinearVelocity().cpy();
        predict.nor();
        predict.scl(25f);

        Vector2 predictPos = body.getPosition().cpy().add(predict);

        Vector2 normal;
        Vector2 target = null;
        float record = Float.MAX_VALUE;

        List<Node> nodes = pathPart.getPath().getNodes();

        for (int i = 0; i < nodes.size(); i++) {

            Vector2 a = nodes.get(i).getState();
            Vector2 b = nodes.get((i + 1) % nodes.size()).getState();

            Vector2 normalPoint = getNormalPoint(a, b, predictPos);

            Vector2 dir = a.cpy().sub(b);

            if (
                    normalPoint.x < Math.min(a.x, b.x) ||
                            normalPoint.x > Math.max(a.x, b.x) ||
                            normalPoint.y < Math.min(a.y, b.y) ||
                            normalPoint.y > Math.max(a.y, b.y)
            ) {
                normalPoint = b.cpy();

                a = nodes.get((i + 1) % nodes.size()).getState();
                b = nodes.get((i + 2) % nodes.size()).getState();
                dir = b.cpy().sub(a);
            }

            float d = predict.dst(normalPoint);

            if (d < record) {
                record = d;

                normal = normalPoint;

                dir.nor();

                dir.scl(25f);

                target = normal.cpy();
                target.add(dir);
            }
        }

        if (record > pathPart.getPath().getRadius()) {
            return this.seek(entity, target);
        } else {
            return Vector2.Zero;
        }
    }

    private Vector2 seek(Entity entity, Vector2 target) {
        Vector2 desired = target.cpy().sub(entity.getPart(TransformPart.class).getPosition2());
        desired.nor();

        float tmpMaxSpeed = 100f;
        desired.scl(tmpMaxSpeed);

        Vector2 steer = desired.sub(entity.getPart(BodyPart.class).getBody().getLinearVelocity());

        float tmpMaxForce = 100f;
        steer.limit(tmpMaxForce);

        return steer;
    }

    private Vector2 getNormalPoint(Vector2 a, Vector2 b, Vector2 p) {
        Vector2 ap = p.cpy().sub(a);
        Vector2 ab = b.cpy().sub(a);
        ab.nor();
        ab.scl(ap.dot(ab));
        return a.cpy().add(ab);
    }
}
