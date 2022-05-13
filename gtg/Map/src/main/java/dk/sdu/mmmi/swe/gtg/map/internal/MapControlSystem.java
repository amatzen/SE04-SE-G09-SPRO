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
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapControlSystem implements IProcessingSystem, MapSPI, IPlugin {
    private static final String MAP_WALLS = "Walls";
    private static final String MAP_ATMS = "Atm";
    private static final String MAP_HOUSES = "Houses";
    private static final float OBJECT_DENSITY = 1f;
    private final float ppm = 1 / 16f;

    private final int tileSize = 64;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private BodyPart collision;

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IEngine engine;

    @Override
    public void addedToEngine() {
    }

    @Override
    public List<Vector2> getATMPositions() {
        ArrayList<Vector2> coordinates = new ArrayList<>();
        final Array<RectangleMapObject> atms = map.getLayers().get(MAP_ATMS).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(atms)) {
            Rectangle rectangle = rObject.getRectangle();
            coordinates.add(rectangle.getPosition(new Vector2()).scl(ppm));
        }
        return coordinates;
    }

    @Override
    public void install(GameData gameData) {
        map = new TmxMapLoader().load("maps/GTG-Map_v5.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, ppm);

        final Array<RectangleMapObject> walls = map.getLayers().get(MAP_WALLS).getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject rObject : new Array.ArrayIterator<>(walls)) {
            Rectangle rectangle = rObject.getRectangle();
            Wall wall = new Wall();
            collision = new BodyPart(shapeFactory.createRectangle(
                    new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2).scl(ppm), // position
                    new Vector2(rectangle.getWidth(), rectangle.getHeight()).scl(ppm), // size
                    BodyDef.BodyType.StaticBody, OBJECT_DENSITY, false));
            collision.getBody().setUserData(wall);
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
    public boolean isTileAccessibly(Vector2 position) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(MAP_HOUSES);
        position = worldPosToMapPos(position);
        return layer.getCell((int) position.x, (int) position.y) == null;
    }

    @Override
    public Vector2 getRandomCellPosition(TiledMapTileLayer layer) {
        TiledMapTileLayer.Cell res = null;

        int x = 0;
        int y = 0;

        while (res == null) {
            x = (int) (Math.random() * layer.getWidth());
            y = (int) (Math.random() * layer.getHeight());
            res = layer.getCell(x, y);
        }

        return new Vector2(x, y);
    }

    @Override
    public MapLayer getLayer(String layerName) {
        return map.getLayers().get(layerName);
    }

    @Override
    public Vector2 worldPosToMapPos(Vector2 worldCoordinates) {
        Vector2 worldPos = new Vector2(worldCoordinates);
        worldPos.scl((1f / tileSize) / ppm);

        return new Vector2(
                (int) worldPos.x,
                (int) worldPos.y
        );
    }

    @Override
    public Vector2 mapPosToWorldPos(Vector2 position) {
        Vector2 mapPos = new Vector2(position);
        return mapPos.scl(1 / ((1f / tileSize) / ppm));
    }

    @Override
    public List<Rectangle> getObstacles() {
        List<Rectangle> obstacles = new ArrayList<>();

        MapLayer layer = map.getLayers().get(MAP_WALLS);

        for (MapObject mapObject : layer.getObjects()) {
            RectangleMapObject object = (RectangleMapObject) mapObject;
            Rectangle rectangle = object.getRectangle();
            obstacles.add(rectangle);
        }

        return obstacles;
    }

    @Override
    public void uninstall(GameData gameData) {
        engine.getEntitiesFor(Family.builder().forEntities(Wall.class).get()).forEach(entity -> {
            engine.removeEntity(entity);
        });
        map.dispose();
    }
}
