package dk.sdu.mmmi.swe.gtg.map.internal;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.CameraPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IPostEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component
public class MapControlSystem implements IPostEntityProcessingService {
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    //float unitScale = 1 / 32f;
    private OrthographicCamera camera;
    private List<? extends Entity> entities;

    @Override
    public void addedToEngine(IEngine engine) {
        entities = engine.getEntitiesFor(Family.builder().with(CameraPart.class).get());
        map = new TmxMapLoader().load("GTG-Map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void process(GameData gameData) {

        if (!entities.isEmpty()) {
            camera = entities.get(0).getPart(CameraPart.class).getCamera();
        }

        if (camera != null) {
            renderer.setView(camera);
            renderer.render();
        }
    }
}
