package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
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

        for (Entity entity : entities) {
            PathPart pathPart = entity.getPart(PathPart.class);

            for (int i = 0; i < pathPart.getPath().size() - 1; i++) {
                shapeRenderer.line(
                        pathPart.getPath().get(i).getState().x,
                        pathPart.getPath().get(i).getState().y,
                        pathPart.getPath().get((i + 1)).getState().x,
                        pathPart.getPath().get(i + 1).getState().y);
            }
        }

        shapeRenderer.end();
    }
}
