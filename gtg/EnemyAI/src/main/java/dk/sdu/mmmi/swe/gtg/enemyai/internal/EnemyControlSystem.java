package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.physics.box2d.Body;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.enemyai.PathPart;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class EnemyControlSystem implements IProcessingSystem, IPlugin {

    private List<? extends Entity> enemies;

    private Entity player;

    private IEntityListener playerListener = new EntityListener() {
        @Override
        public void onEntityAdded(Entity plr) {
            player = plr;

            for (Entity enemy : enemies) {
                addPathFindingPart(enemy, plr);
            }
        }

        @Override
        public void onEntityRemoved(Entity entity) {
            for (Entity enemy : enemies) {
                enemy.removePart(PathFindingPart.class);
                enemy.removePart(PathPart.class);
            }
        }
    };

    private IEntityListener enemyListener = new EntityListener() {
        @Override
        public void onEntityAdded(Entity enemy) {
            if (player != null) {
                addPathFindingPart(enemy, player);
            }
        }
    };

    @Override
    public void addedToEngine(IEngine engine) {
        enemies = engine.getEntitiesFor(
            Family.builder().forEntities(Enemy.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        for (Entity enemy : enemies) {
            Body entityBody = enemy.getPart(BodyPart.class).getBody();

            float velDir = entityBody.getLinearVelocity().angleRad() - (float) Math.PI * 0.5f;
            float orientation = entityBody.getAngle();

            entityBody.setTransform(entityBody.getPosition(), velDir);
        }
    }

    private void addPathFindingPart(Entity enemy, Entity player) {
        Body source = enemy.getPart(BodyPart.class).getBody();
        Body target = player.getPart(BodyPart.class).getBody();

        PathFindingPart pathFindingPart = new PathFindingPart(target.getPosition(), source.getPosition());

        enemy.addPart(pathFindingPart);
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        engine.addEntityListener(
            Family.builder().with(PlayerPart.class).get(),
            playerListener,
            true
        );

        engine.addEntityListener(
            Family.builder().forEntities(Enemy.class).get(),
            enemyListener,
            true
        );
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(
            Family.builder().with(PlayerPart.class).get(),
            playerListener
        );

        engine.removeEntityListener(
            Family.builder().forEntities(Enemy.class).get(),
            enemyListener
        );
    }
}
