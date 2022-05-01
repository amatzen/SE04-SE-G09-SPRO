import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.enemyai.internal.AStar;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class AStarTest {

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

        AStar aStar = new AStar(map);

        List<Node> res = aStar.searchNodePath(new Vector2(5, 5), new Vector2(15, 15));

        System.out.println(res.stream().map(n -> n.getState().toString()).collect(Collectors.toList()));
    }

    @Test
    public void testHashSet() {
        Set<Node> nodeSet = new HashSet<>();

        Node node1 = new Node(null, new Vector2(1, 1), 0);
        Node node2 = new Node(null, new Vector2(1, 1), 0);

        nodeSet.add(node1);

        assertTrue(nodeSet.contains(node2));
    }

    @Test
    public void queueTest() {
        Queue<Node> queue = new PriorityQueue<>((o1, o2) -> {
            return (int) (o1.getCost() - o2.getCost());
        });

        Node node1 = new Node(null, new Vector2(1, 1), 2);
        Node node2 = new Node(null, new Vector2(1, 1), 4);

        queue.add(node1);
        queue.add(node2);
        queue.add(node1);
        queue.add(node1);

        System.out.println(queue);

        getBest(queue);

        System.out.println(queue);

        getBest(queue);

        System.out.println(queue);

        getBest(queue);

        System.out.println(queue);
    }

    public Node getBest(Queue<Node> fringe) {
        return fringe.poll();
    }

}
