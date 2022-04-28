package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.PathPart;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class PathRenderer implements IPostEntityProcessingService {
    private List<? extends Entity> entities;
    private ShapeRenderer shapeRenderer;

    @Override
    public void addedToEngine(IEngine engine) {
        entities = engine.getEntitiesFor(
                Family.builder().with(PathPart.class).get()
        );

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void process(GameData gameData) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 1, 1);

        shapeRenderer.setProjectionMatrix(gameData.getCamera().combined);

        for (Entity entity : entities) {
            PathPart pathPart = entity.getPart(PathPart.class);
            drawPath(pathPart.getPath(), shapeRenderer);
        }

        shapeRenderer.end();
    }

    private void drawPath(List<Node> path, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < path.size() - 1; i++) {
            shapeRenderer.line(
                    path.get(i).getState().x,
                    path.get(i).getState().y,
                    path.get((i + 1)).getState().x,
                    path.get(i + 1).getState().y);
        }
    }
}
