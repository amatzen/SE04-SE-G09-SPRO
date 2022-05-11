package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.WantedPart;
import dk.sdu.mmmi.swe.gtg.common.family.EntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IEntityListener;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.common.signals.ISignalListener;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.enemyai.Enemy;
import dk.sdu.mmmi.swe.gtg.enemyai.services.IEnemyFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class EnemySpawnerSystem implements IPlugin {

    private IFamily enemyFamily;
    private IFamily targetFamily;
    private IEntityListener enemyListener;
    private IEntityListener targetListener;

    private ISignalListener<WantedPart> wantedLevelListener;

    @Reference
    private IEnemyFactory enemyFactory;

    private int maxEnemyCount;
    private List<? extends Entity> enemyList;

    private List<? extends Entity> targets;

    @Reference
    private MapSPI map;

    private IEngine engine;

    public EnemySpawnerSystem() {
        enemyFamily = Family.builder().forEntities(Enemy.class).get();
        targetFamily = Family.builder().with(WantedPart.class, TransformPart.class).get();
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        this.engine = engine;

        enemyList = engine.getEntitiesFor(enemyFamily);

        wantedLevelListener = (signal, wantedPart) -> {
            onWantedLevelUpdated(wantedPart);
        };

        this.targetListener = new EntityListener() {
            @Override
            public void onEntityAdded(Entity entity) {
                WantedPart wantedPart = entity.getPart(WantedPart.class);
                wantedPart.wantedLevelUpdated.add(wantedLevelListener);
                onWantedLevelUpdated(wantedPart);
                System.out.println("stfu bro");
            }
        };

        enemyListener = new EntityListener() {
            @Override
            public void onEntityRemoved(Entity entity) {
                onEnemyDied(entity);
            }
        };

        engine.addEntityListener(targetFamily, targetListener, true);
        engine.addEntityListener(enemyFamily, enemyListener);
    }

    private void onEnemyDied(Entity entity) {
        updateEnemies();
    }

    private void updateEnemies() {
        if (this.enemyList.size() < maxEnemyCount) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        // new Vector2(134.28f, 84)
        engine.addEntity(
            enemyFactory.createEnemy(findRandomSpawnPoint())
        );
    }

    private Vector2 findRandomSpawnPoint() {
        TiledMapTileLayer roads = (TiledMapTileLayer) map.getLayer("Roads");
        Vector2 spawnPoint = map.getRandomCellPosition(roads);

        return spawnPoint;
    }

    private void onWantedLevelUpdated(WantedPart wantedPart) {
        int wantedLevel = wantedPart.getWantedLevel();
        int numberOfEnemies = wantedLevel * wantedLevel;

        this.maxEnemyCount = numberOfEnemies;

        updateEnemies();
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        engine.removeEntityListener(enemyFamily, enemyListener);
        engine.removeEntityListener(targetFamily, targetListener);

        engine.getEntitiesFor(enemyFamily).stream().map(e -> e.getPart(WantedPart.class)).forEach(wantedPart -> {
            wantedPart.wantedLevelUpdated.remove(wantedLevelListener);
        });

        engine.getEntitiesFor(
                Family.builder().forEntities(Enemy.class).get()
        ).forEach(engine::removeEntity);
    }
}
