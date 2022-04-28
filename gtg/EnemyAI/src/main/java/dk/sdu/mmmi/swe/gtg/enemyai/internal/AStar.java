package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;

import java.util.*;

public class AStar {

    private MapSPI map;

    public AStar(MapSPI map) {
        this.map = map;
    }

    public List<Node> searchNodePath(Vector2 from, Vector2 to) {
        Node start = new Node(null, from, 1);
        Node goal = new Node(null, to, 1);

        return treeSearch(start, goal);
    }

    public List<Node> treeSearch(final Node start, final Node goal) {
        Set<Node> explored = new HashSet<>();
        PriorityQueue<Node> fringe = new PriorityQueue<>((node1, node2) -> (int) (f(node1, goal) - f(node2, goal)));

        fringe.add(start);

        while (!fringe.isEmpty()) {
            Node current = getBest(fringe, goal);

            explored.add(current);

            System.out.println("-----------");
            System.out.println("Current " + map.worldCoordinatesToMapCoordinates(current.getState()));
            System.out.println("Cost " + current.getCost());
            System.out.println("Goal " + map.worldCoordinatesToMapCoordinates(goal.getState()));
            System.out.println("Size " + fringe.size());

            if (map.worldCoordinatesToMapCoordinates(current.getState())
                .equals(
                    map.worldCoordinatesToMapCoordinates(goal.getState())
            )) {
                System.out.println("Found goal");
                return reconstructPath(current);
            }

            for (Node neighbor : expand(current)) {
                if (!explored.contains(neighbor)) {
                    fringe.add(neighbor);
                } else {
                    continue;
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Node> expand(Node current) {
        return getNeighbors(current);
    }

    private List<Node> getNeighbors(Node current) {
        ArrayList<Node> neighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Vector2 neighbor = new Vector2(current.getState());
            switch (i) {
                case 0:
                    neighbor.x += 1;
                    break;
                case 1:
                    neighbor.x -= 1;
                    break;
                case 2:
                    neighbor.y += 1;
                    break;
                case 3:
                    neighbor.y -= 1;
                    break;
            }

            if (isAccessible(neighbor)) {
                neighbors.add(new Node(current, neighbor, current.getCost() + 1));
            } else {
                System.out.println("Not accessible");
            }
        }

        return neighbors;
    }

    private boolean isAccessible(Vector2 pos) {
        return map.isTileAccessibly(pos.x, pos.y);
    }

    private List<Node> reconstructPath(Node current) {
        List<Node> path = new LinkedList<>();

        while (current != null) {
            path.add(0, current);
            current = current.getParent();
        }

        return path;
    }

    public Node getBest(PriorityQueue<Node> fringe, Node goal) {
        return fringe.poll();
    }

    private float f(Node node, Node goal) {
        return g(node, goal) + 100 * h(node, goal);
    }

    private float g(Node node, Node goal) {
        return node.getCost();
    }

    private float h(Node node, Node goal) {
        return (float) Math.sqrt(Math.pow(node.getState().x - goal.getState().x, 2) + Math.pow(Math.abs(node.getState().y - goal.getState().y), 2));
    }
}
