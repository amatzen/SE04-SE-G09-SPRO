package dk.sdu.mmmi.swe.gtg.pathfindingsystem.internal;

import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.PathFindingPart;
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
    private float counter = 0;

    public PathFindingSystem() {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    @Override
    public void addedToEngine() {
        entities = engine.getEntitiesFor(
                Family.builder().with(PathFindingPart.class).get()
        );
    }

    @Override
    public void process(GameData gameData) {
        if (counter > 0.5f) {
            searchPaths();
            counter = 0;
        }

        counter += gameData.getDelta();
    }

    private void searchPaths() {
        CountDownLatch latch = new CountDownLatch(entities.size());

        for (Entity entity : entities) {
            executorService.execute(() -> {
                searchPath(entity);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPath(Entity entity) {
        PathFindingPart pathFindingPart = entity.getPart(PathFindingPart.class);

        Path path = pathFinding.searchNodePath(pathFindingPart.getStart(), pathFindingPart.getEnd(), mapSPI);

        if (entity.hasPart(PathPart.class)) {
            PathPart pathPart = entity.getPart(PathPart.class);
            pathPart.setPath(path);
        } else {
            entity.addPart(new PathPart(path));
        }
    }
}
