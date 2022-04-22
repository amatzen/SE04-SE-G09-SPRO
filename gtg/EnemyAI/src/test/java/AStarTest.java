import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.internal.AStar;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class AStarTest {

    @Test
    public void testAStar() {
        MapSPI map = new MapSPI() {
            @Override
            public boolean isTileAccessibly(float x, float y) {
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

        AStar aStar = new AStar(map);

        List<Node> res = aStar.searchNodePath(new Vector2(5, 5), new Vector2(15, 15));

        System.out.println(res.stream().map(n -> n.getState().toString()).collect(Collectors.toList()));
    }

}
