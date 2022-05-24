package dk.sdu.mmmi.swe.gtg.pathfindingsystem.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PathFindingPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PlayerPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Path;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.PathPart;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.services.IPathFinding;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PathFindingSystem implements IProcessingSystem {

    private List<? extends Entity> entities;

    @Reference
    private MapSPI mapSPI;

    @Reference
    private IPathFinding pathFinding;

    @Reference
    private IEngine engine;

    private ExecutorService executorService;

    public PathFindingSystem() {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    private List<? extends Entity> player;

    @Override
    public void addedToEngine() {
        entities = engine.getEntitiesFor(
            Family.builder().with(TransformPart.class, PathFindingPart.class).get()
        );

        player = engine.getEntitiesFor(
                Family.builder().with(PlayerPart.class).get()
        );
    }

    private float counter = 0;

    @Override
    public void process(GameData gameData) {
        if (counter > 0.5f) {
            searchPaths();
            counter = 0;
        }

        counter += gameData.getDelta();
    }

    private void searchPaths() {
        if (this.player.size() <= 0) {
            return;
        }

        Entity player = this.player.get(0);

        CountDownLatch latch = new CountDownLatch(entities.size());

        for (Entity entity : entities) {
            executorService.execute(() -> {
                searchPath(entity, player.getPart(TransformPart.class).getPosition2());
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPath(Entity entity, Vector2 target) {
        TransformPart transformPart = entity.getPart(TransformPart.class);

        Path path = pathFinding.searchNodePath(transformPart.getPosition2(), target, mapSPI);

        if (entity.hasPart(PathPart.class)) {
            PathPart pathPart = entity.getPart(PathPart.class);
            pathPart.setPath(path);
        } else {
            entity.addPart(new PathPart(path));
        }
    }
}
