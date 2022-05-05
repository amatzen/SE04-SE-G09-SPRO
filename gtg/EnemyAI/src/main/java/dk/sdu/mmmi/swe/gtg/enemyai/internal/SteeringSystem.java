package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SteeringPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.Path;
import dk.sdu.mmmi.swe.gtg.enemyai.PathPart;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component
public class SteeringSystem implements IEntityProcessingService {

    private List<? extends Entity> entities;

    private AStar aStar;

    @Reference
    private MapSPI mapSPI;

    public SteeringSystem() {
    }

    @Override
    public void addedToEngine(IEngine engine) {
        aStar = new AStar(mapSPI);

        entities = engine.getEntitiesFor(
            Family.builder().with(TransformPart.class, SteeringPart.class).get()
        );
    }

    private float counter = 0;

    @Override
    public void process(GameData gameData) {
        if (counter > 0.5f) {
            searchPaths(gameData);
            counter = 0;
        }

        counter += gameData.getDelta();
    }

    private void searchPaths(GameData gameData) {
        for (Entity entity : entities) {
            TransformPart transformPart = entity.getPart(TransformPart.class);

            Path path = aStar.searchNodePath(transformPart.getPosition2(), new Vector2(126, 74));

            if (entity.hasPart(PathPart.class)) {
                PathPart pathPart = entity.getPart(PathPart.class);
                pathPart.setPath(path);
            } else {
                entity.addPart(new PathPart(path));
            }
        }
    }
}
