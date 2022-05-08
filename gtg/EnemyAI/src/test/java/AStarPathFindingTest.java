import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.internal.AStarPathFinding;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class AStarPathFindingTest {

    @Test
    public void testAStar() {
        MapSPI map = new MapSPI() {
            @Override
            public boolean isTileAccessibly(float x, float y) {
                if (x > 8 && x < 10 && y > 4 && y < 16) {
                    return false;
                }
                return true;
            }

            @Override
            public Vector2 worldCoordinatesToMapCoordinates(Vector2 worldCoordinates) {
                return worldCoordinates;
            }

            @Override
            public Vector2 tileCoordinatesToWorldCoordinates(Vector2 tileCoordinates) {
                return tileCoordinates;
            }

            @Override
            public List<Rectangle> getObstacles() {
                return null;
            }
        };

        AStarPathFinding aStar = new AStarPathFinding(map);


        List<Node> res = aStar.searchNodePath(new Vector2(5, 5), new Vector2(15, 15)).getNodes();

        assertTrue("Should have found a path", res.size() > 0);
        assertTrue("Should have found a path", res.get(0).getState().equals(new Vector2(5, 5)));

        System.out.println(res.stream().map(n -> n.getState().toString()).collect(Collectors.toList()));
    }

    @Test
    public void noClearPath() {
        MapSPI map = new MapSPI() {
            @Override
            public boolean isTileAccessibly(float x, float y) {
                if (x > 10 || x < -10 || y > 10 || y < -10) {
                    return false;
                }
                return true;
            }

            @Override
            public Vector2 worldCoordinatesToMapCoordinates(Vector2 worldCoordinates) {
                return worldCoordinates;
            }

            @Override
            public Vector2 tileCoordinatesToWorldCoordinates(Vector2 tileCoordinates) {
                return tileCoordinates;
            }

            @Override
            public List<Rectangle> getObstacles() {
                return null;
            }
        };

        AStarPathFinding aStar = new AStarPathFinding(map);

        List<Node> res = aStar.searchNodePath(new Vector2(5, 5), new Vector2(15, 15)).getNodes();

        assertTrue("No path should be found", res.isEmpty());

        System.out.println(res.stream().map(n -> n.getState().toString()).collect(Collectors.toList()));
    }

}
