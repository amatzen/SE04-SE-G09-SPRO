package dk.sdu.mmmi.swe.gtg.map.internal;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapControlSystem implements IEntityProcessingService, MapSPI {
    private static final String MAP_WALL = "Walls";
    private static final String MAP_HOUSES = "Houses";
    private static final float OBJECT_DENSITY = 1f;
    private final float unitScale = 1 / 16f;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private BodyPart collision;

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Override
    public void addedToEngine(IEngine engine) {
        map = new TmxMapLoader().load("maps/GTG-Map1_v3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        final Array<RectangleMapObject> walls = map.getLayers().get(MAP_WALL).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<RectangleMapObject>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            Wall wall = new Wall();
            collision = new BodyPart(shapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2).scl(unitScale), // position
                    new Vector2(rectangle.getWidth(), rectangle.getHeight()).scl(unitScale), // size
                    BodyDef.BodyType.StaticBody, OBJECT_DENSITY, false));
            wall.addPart(collision);
            engine.addEntity(wall);
        }
    }

    @Override
    public void process(GameData gameData) {

        renderer.setView(gameData.getCamera());
        renderer.render();
    }

    @Override
    public boolean isTileAccessibly(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(MAP_HOUSES);
        return layer.getCell((int) (x * unitScale), (int) (y * unitScale)) == null;
    }

    @Override
    public Vector2 worldCoordinatesToMapCoordinates(Vector2 worldCoordinates) {
        Vector2 worldPos = new Vector2(worldCoordinates);
        worldPos.scl(unitScale * 4f);

        return new Vector2(
                (int) worldPos.x,
                (int) worldPos.y
        );
    }

    @Override
    public Vector2 tileCoordinatesToWorldCoordinates(Vector2 tileCoordinates) {
        return tileCoordinates.scl(unitScale);
    }

    @Override
    public List<Rectangle> getObstacles() {
        List<Rectangle> obstacles = new ArrayList<>();

        MapLayer layer = map.getLayers().get(MAP_WALL);

        for (MapObject mapObject : layer.getObjects()) {
            RectangleMapObject object = (RectangleMapObject) mapObject;
            Rectangle rectangle = object.getRectangle();
            obstacles.add(rectangle);
        }

        return obstacles;
    }
}
