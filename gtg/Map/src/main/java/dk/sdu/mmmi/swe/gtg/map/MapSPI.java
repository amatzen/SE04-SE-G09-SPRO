package dk.sdu.mmmi.swe.gtg.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface MapSPI {
    boolean isTileAccessibly(float x, float y);

    Vector2 worldCoordinatesToMapCoordinates(Vector2 worldCoordinates);

    Vector2 tileCoordinatesToWorldCoordinates(Vector2 tileCoordinates);

    List<Rectangle> getObstacles();
}
