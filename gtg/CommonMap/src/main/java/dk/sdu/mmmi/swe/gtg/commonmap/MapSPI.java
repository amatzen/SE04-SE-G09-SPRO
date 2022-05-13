package dk.sdu.mmmi.swe.gtg.commonmap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface MapSPI {

    public List<Vector2> getATMPositions();

    boolean isTileAccessibly(Vector2 position);

    Vector2 getRandomCellPosition(TiledMapTileLayer layer);

    MapLayer getLayer(String layerName);

    Vector2 worldPosToMapPos(Vector2 worldCoordinates);

    Vector2 mapPosToWorldPos(Vector2 position);

    List<Rectangle> getObstacles();

}
