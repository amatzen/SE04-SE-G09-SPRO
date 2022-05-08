package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class EnemyControlSystem implements IProcessingSystem {

    private List<? extends Entity> enemies;

    @Override
    public void addedToEngine(IEngine engine) {
        enemies = engine.getEntitiesFor(
            Family.builder().forEntities(Enemy.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        enemies.forEach(enemy -> {
            System.out.println("EnemyControlSystem");
            Body entityBody = enemy.getPart(BodyPart.class).getBody();

            float velDir = entityBody.getLinearVelocity().angleRad() - (float) Math.PI * 0.5f;
            float orientation = entityBody.getAngle();

            entityBody.setTransform(entityBody.getPosition(), velDir);
        });
    }
}
