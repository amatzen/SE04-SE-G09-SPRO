import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.pathfinding.internal.AStarPathFinding;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Node;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

public class AStarPathFindingTest {

    @Test
    public void testAStar() {
        MapSPI map = Mockito.mock(MapSPI.class);

        Mockito.when(map.isTileAccessibly(Mockito.any(Vector2.class))).thenReturn(true);
        Mockito.when(map.worldPosToMapPos(Mockito.any(Vector2.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        AStarPathFinding aStar = new AStarPathFinding();

        final int resolution = 1;
        final Vector2 start = new Vector2(5, 5);
        final Vector2 end = new Vector2(15, 15);

        List<Node> res = aStar.searchNodePath(start, end, map, resolution).getNodes();

        Assert.assertTrue("Should have found a path", res.size() > 0);
        Assert.assertTrue("Should have found a path", res.get(0).getState().equals(new Vector2(5, 5)));

        System.out.println(res.stream().map(n -> n.getState().toString()).collect(Collectors.toList()));
    }

    @Test
    public void noClearPath() {
        MapSPI map = Mockito.mock(MapSPI.class);

        Mockito.when(map.isTileAccessibly(Mockito.any(Vector2.class))).thenAnswer(invocationOnMock -> {
            Vector2 position = (Vector2) invocationOnMock.getArguments()[0];
            if (position.x > 10 || position.x < -10 || position.y > 10 || position.y < -10) {
                return false;
            }
            return true;
        });

        Mockito.when(map.worldPosToMapPos(Mockito.any(Vector2.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        AStarPathFinding aStar = new AStarPathFinding();

        final int resolution = 1;
        final Vector2 start = new Vector2(5, 5);
        final Vector2 end = new Vector2(15, 15);

        List<Node> res = aStar.searchNodePath(start, end, map, resolution).getNodes();

        Assert.assertTrue("No path should be found", res.isEmpty());
    }

}
