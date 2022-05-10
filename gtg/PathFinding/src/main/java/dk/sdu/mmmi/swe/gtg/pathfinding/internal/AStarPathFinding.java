package dk.sdu.mmmi.swe.gtg.pathfinding.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Node;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Path;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.services.IPathFinding;
import org.osgi.service.component.annotations.Component;

import java.util.*;

@Component
public class AStarPathFinding implements IPathFinding {
    @Override
    public Path searchNodePath(final Vector2 from, final Vector2 to, MapSPI map) {
        return searchNodePath(from, to, map, 1);
    }

    @Override
    public Path searchNodePath(final Vector2 from, final Vector2 to, MapSPI map, final int resolution) {
        Node start = new Node(null, from, 0);
        Node goal = new Node(null, to, 0);

        return bestFirstSearch(start, goal, map, resolution);
    }

    private Path bestFirstSearch(final Node start, final Node goal, MapSPI map, final int resolution) {
        Map<String, Node> explored = new HashMap<>();
        Map<String, Node> inFringe = new HashMap<>();
        PriorityQueue<Node> fringe = new PriorityQueue<>(
                (node1, node2) -> Float.compare(f(node1, goal), f(node2, goal))
        );

        start.setState(new Vector2((int) start.getState().x, (int) start.getState().y));
        goal.setState(new Vector2((int) goal.getState().x, (int) goal.getState().y));

        addToFringe(start, fringe, inFringe);

        while (!fringe.isEmpty()) {
            Node current = getBest(fringe);

            if (current.getKey().equals(goal.getKey())) {
                return reconstructPath(current);
            }

            inFringe.remove(current.getKey());
            explored.put(current.getKey(), current);

            for (Node neighbor : expand(current, map, resolution)) {
                if (explored.containsKey(neighbor.getKey())) {
                    continue;
                }

                Node inFringeNode = inFringe.get(neighbor.getKey());

                if (inFringeNode != null && neighbor.getCost() >= inFringeNode.getCost()) {
                    continue;
                }

                addToFringe(neighbor, fringe, inFringe);
            }
        }

        return new Path();
    }

    private void addToFringe(Node node, Queue<Node> fringe, Map<String, Node> inFringe) {
        fringe.add(node);
        inFringe.put(node.getKey(), node);
    }

    private List<Node> expand(Node current, MapSPI map, int resolution) {
        return getNeighbors(current, map, resolution);
    }

    private List<Node> getNeighbors(Node current, MapSPI map, int resolution) {
        ArrayList<Node> neighbors = new ArrayList<>();

        float diagonalScalar = (float) Math.sqrt(2);

        for (int i = 0; i < 8; i++) {
            Vector2 neighborPosition = new Vector2(current.getState());

            float baseCost = current.getCost();


            switch (i) {
                case 0:
                    neighborPosition.x += resolution;
                    baseCost += resolution;
                    break;
                case 1:
                    neighborPosition.x -= resolution;
                    baseCost += resolution;
                    break;
                case 2:
                    neighborPosition.y += resolution;
                    baseCost += resolution;
                    break;
                case 3:
                    neighborPosition.y -= resolution;
                    baseCost += resolution;
                    break;
                case 4:
                    neighborPosition.x += resolution;
                    neighborPosition.y += resolution;
                    baseCost += resolution * diagonalScalar;
                    break;
                case 5:
                    neighborPosition.x -= resolution;
                    neighborPosition.y += resolution;
                    baseCost += resolution * diagonalScalar;
                    break;
                case 6:
                    neighborPosition.x += resolution;
                    neighborPosition.y -= resolution;
                    baseCost += resolution * diagonalScalar;
                    break;
                case 7:
                    neighborPosition.x -= resolution;
                    neighborPosition.y -= resolution;
                    baseCost += resolution * diagonalScalar;
                    break;
            }

            if (isAccessible(neighborPosition, map)) {
                neighbors.add(new Node(current, neighborPosition, baseCost));
            }
        }

        return neighbors;
    }

    private boolean isAccessible(Vector2 pos, MapSPI map) {
        return map.isTileAccessibly(pos.x, pos.y);
    }

    private Path reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            current = current.getParent();
        }

        Collections.reverse(path);

        return new Path(path);
    }

    private Node getBest(Queue<Node> fringe) {
        return fringe.poll();
    }

    private float f(Node node, Node goal) {
        return g(node, goal) + h(node, goal);
    }

    private float g(Node node, Node goal) {
        return node.getCost();
    }

    private float h(Node node, Node goal) {
        return (float) Math.sqrt(Math.pow(node.getState().x - goal.getState().x, 2) + Math.pow(node.getState().y - goal.getState().y, 2));
    }
}
