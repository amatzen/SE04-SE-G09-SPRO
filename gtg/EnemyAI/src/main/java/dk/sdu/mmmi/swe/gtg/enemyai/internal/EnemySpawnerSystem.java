package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.enemyai.services.IEnemyFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class EnemySpawnerSystem implements IPlugin {

    private IFamily enemyFamily;
    private IEntityListener enemyListener;

    @Reference
    private IEnemyFactory enemyFactory;

    private List<? extends Entity> enemyList;

    public EnemySpawnerSystem() {
        enemyFamily = Family.builder().forEntities(Enemy.class).get();
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        enemyList = engine.getEntitiesFor(enemyFamily);

        enemyListener = new EntityListener() {
            @Override
            public void onEntityRemoved(Entity entity) {
                System.out.println("Enemy removed");
                engine.addEntity(enemyFactory.createEnemy(new Vector2(134.28f, 84)));
            }
        };

        engine.addEntityListener(enemyFamily, enemyListener);

        engine.addEntity(enemyFactory.createEnemy(new Vector2(134.28f, 84)));
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(enemyFamily, enemyListener);

        engine.getEntitiesFor(
                Family.builder().forEntities(Enemy.class).get()
        ).forEach(engine::removeEntity);
    }
}
