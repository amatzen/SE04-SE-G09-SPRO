package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.screens.commonscreen.ScreenManagerSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class BustedSystem implements IPlugin, IProcessingSystem {

    private List<? extends Entity> enemies;
    private Entity player;

    private float accumulatedDistance = 0;
    private float timer = 0;

    private float accumulatedEnemyDistance = 0;

    private final float interval = 5f;
    private final float minAverageSpeed = 2f;
    private final float maxAverageDistance = 5f;

    @Reference
    private ScreenManagerSPI screenManager;

    private IEntityListener playerListener = new EntityListener() {
        @Override
        public void onEntityAdded(Entity entity) {
            player = entity;
        }

        @Override
        public void onEntityRemoved(Entity entity) {
            player = null;
        }
    };

    @Override
    public void addedToEngine(IEngine engine) {
    }

    @Override
    public void process(GameData gameData) {
        if (player != null && !enemies.isEmpty()) {
            BodyPart playerBody = player.getPart(BodyPart.class);

            if (timer > interval) {
                float averageSpeed = accumulatedDistance / timer;
                float averageEnemyDistance = accumulatedEnemyDistance / timer;

                if (averageSpeed < minAverageSpeed && averageEnemyDistance < maxAverageDistance) {
                    screenManager.changeScreen("BustedScreen");
                }

                accumulatedDistance = 0;
                accumulatedEnemyDistance = 0;
                timer = 0;
            }

            timer += gameData.getDelta();
            accumulatedDistance += playerBody.getBody().getLinearVelocity().len() * gameData.getDelta();
            accumulatedEnemyDistance += getNearestEnemyDistance(player, enemies) * gameData.getDelta();
        }
    }

    private float getNearestEnemyDistance(Entity player, List<? extends Entity> enemies) {
        Vector2 playerPosition = player.getPart(TransformPart.class).getPosition2();

        float nearestEnemyDistance = Float.MAX_VALUE;
        for (Entity enemy : enemies) {
            Vector2 enemyPosition = enemy.getPart(TransformPart.class).getPosition2();
            float enemyDistance = enemyPosition.dst(playerPosition);
            if (enemyDistance < nearestEnemyDistance) {
                nearestEnemyDistance = enemyDistance;
            }
        }

        return nearestEnemyDistance;
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        engine.addEntityListener(Family.builder().with(PlayerPart.class).get(), playerListener, true);
        enemies = engine.getEntitiesFor(Family.builder().forEntities(Enemy.class).get());
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(Family.builder().with(PlayerPart.class).get(), playerListener);
    }
}
