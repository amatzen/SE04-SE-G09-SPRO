package dk.sdu.mmmi.swe.gtg.pathrenderingsystem.internal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Node;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Path;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.PathPart;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/*
 * Remove comment and import class to show path rendering
 */

//@Component
public class PathRenderingSystem implements IPostProcessingSystem {
    private List<? extends Entity> entities;
    private ShapeRenderer shapeRenderer;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
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

    private void drawPath(Path path, ShapeRenderer shapeRenderer) {
        drawPath(path.getNodes(), shapeRenderer);
    }

    private void drawPath(List<Node> nodes, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < nodes.size() - 1; i++) {
            shapeRenderer.line(
                    nodes.get(i).getState().x,
                    nodes.get(i).getState().y,
                    nodes.get((i + 1)).getState().x,
                    nodes.get(i + 1).getState().y);
        }
    }
}
