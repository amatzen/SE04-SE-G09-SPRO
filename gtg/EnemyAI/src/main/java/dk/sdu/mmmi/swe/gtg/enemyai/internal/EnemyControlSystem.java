package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class EnemyControlSystem implements IProcessingSystem {

    private List<? extends Entity> enemies;

    private List<? extends Entity> player;

    @Override
    public void addedToEngine(IEngine engine) {
        enemies = engine.getEntitiesFor(
            Family.builder().forEntities(Enemy.class).get()
        );

        player = engine.getEntitiesFor(
            Family.builder().with(PlayerPart.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        Entity player = null;
        if (!this.player.isEmpty()) {
            player = this.player.get(0);
        }

        for (Entity enemy : enemies) {
            if (player != null) {
                addPathFindingPart(enemy, player);
            }

            Body entityBody = enemy.getPart(BodyPart.class).getBody();

            float velDir = entityBody.getLinearVelocity().angleRad() - (float) Math.PI * 0.5f;
            float orientation = entityBody.getAngle();

            entityBody.setTransform(entityBody.getPosition(), velDir);
        }
    }

    private void addPathFindingPart(Entity enemy, Entity player) {
        TransformPart sourceTransform = enemy.getPart(TransformPart.class);
        TransformPart targetTransform = player.getPart(TransformPart.class);

        PathFindingPart pathFindingPart = new PathFindingPart(targetTransform.getPosition2(), sourceTransform.getPosition2());

        enemy.addPart(pathFindingPart);
    }
}
