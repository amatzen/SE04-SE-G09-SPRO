package dk.sdu.mmmi.swe.gtg.enemyai.internal;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.enemyai.Node;
import dk.sdu.mmmi.swe.gtg.map.MapSPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AStar {

    private MapSPI map;

    public AStar(MapSPI map) {
        this.map = map;
    }

    public List<Node> searchNodePath(Vector2 from, Vector2 to) {
        Node start = new Node(null, from, 0);
        Node goal = new Node(null, to, 0);

        return treeSearch(start, goal);
    }

    public List<Node> treeSearch(final Node start, final Node goal) {
        ArrayList<Node> fringe = new ArrayList<>();

        fringe.add(start);

        /*
        Node current = getBest(fringe);

        System.out.println(map.worldCoordinatesToMapCoordinates(current.getState()));
        System.out.println(map.worldCoordinatesToMapCoordinates(goal.getState()));

        System.out.println(map.isTileAccessibly(current.getState().x, current.getState().y));
        */
        while (!fringe.isEmpty()) {
            Node current = getBest(fringe, goal);

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

            fringe.addAll(expand(current));
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

    public Node getBest(ArrayList<Node> fringe, Node goal) {
        int bestIndex = 0;
        Node best = fringe.get(bestIndex);
        float min = f(best, goal);

        for (int i = 1; i < fringe.size(); i++) {
            if (f(fringe.get(i), goal) < min) {
                best = fringe.get(i);
                bestIndex = i;
                min = f(best, goal);
            }
        }

        fringe.remove(bestIndex);

        return best;
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
