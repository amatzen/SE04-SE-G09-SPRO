package dk.sdu.mmmi.swe.gtg.map.internal;

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
    private float unitScale = 1 / 16f;
    private List<? extends Entity> entities;

    @Override
    public void addedToEngine(IEngine engine) {
        entities = engine.getEntitiesFor(Family.builder().with(CameraPart.class).get());
        map = new TmxMapLoader().load("maps/GTG-Map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
    }

    @Override
    public void process(GameData gameData) {

        renderer.setView(gameData.getCamera());
        renderer.render();
    }
}
