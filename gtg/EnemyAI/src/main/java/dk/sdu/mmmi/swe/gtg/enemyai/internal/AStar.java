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

    private float resolution = 1f;
    public List<Node> searchNodePath(Vector2 from, Vector2 to) {
        Node start = new Node(null, from, 0);
        Node goal = new Node(null, to, 0);

        return treeSearch(start, goal);
    }

    public List<Node> treeSearch(final Node start, final Node goal) {
        Set<Node> explored = new HashSet<>();
        Map<String, Node> inFringe = new HashMap<>();
        PriorityQueue<Node> fringe = new PriorityQueue<>((node1, node2) -> (int) (f(node1, goal) - f(node2, goal)));

        start.setState(new Vector2((int) start.getState().x, (int) start.getState().y));
        goal.setState(new Vector2((int) goal.getState().x, (int) goal.getState().y));
        fringe.add(start);

        Node last = null;

        while (!fringe.isEmpty()) {
            Node current = fringe.remove();

            System.out.println("-----------");
            System.out.println("Current " + current.getState());
            System.out.println("Cost " + current.getCost());
            System.out.println("Goal " + goal.getState());
            System.out.println("Size " + fringe.size());

            System.out.println("Current equals last: " + current.equals(last));
            System.out.println("Queue contains current: " + fringe.contains(current));
            System.out.println("Queue contains last: " + fringe.contains(last));
            System.out.println("Explored contains current: " + explored.contains(current));
            System.out.println("Explored contains last: " + explored.contains(last));

            last = current;

            if (current.equals(goal)) {
                System.out.println("Found goal");
                return reconstructPath(current);
            }

            fringe.remove(current);
            explored.add(current);

            for (Node neighbor : expand(current)) {
                if (explored.contains(neighbor)) {
                    continue;
                }

                fringe.add(neighbor);
            }
        }

        return Collections.emptyList();
    }

    private String getKey(Node node) {
        return Integer.toString((int) node.getState().x) + "," + Integer.toString((int) node.getState().y);
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
                    neighbor.x += resolution;
                    break;
                case 1:
                    neighbor.x -= resolution;
                    break;
                case 2:
                    neighbor.y += resolution;
                    break;
                case 3:
                    neighbor.y -= resolution;
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

    public Node getBest(Queue<Node> fringe) {
        return fringe.poll();
    }

    private float f(Node node, Node goal) {
        return g(node, goal) + h(node, goal);
    }

    private float g(Node node, Node goal) {
        return node.getCost();
    }

    private float h(Node node, Node goal) {
        return (float) Math.sqrt(Math.pow(node.getState().x - goal.getState().x, 2) + Math.pow(Math.abs(node.getState().y - goal.getState().y), 2));
    }
}
