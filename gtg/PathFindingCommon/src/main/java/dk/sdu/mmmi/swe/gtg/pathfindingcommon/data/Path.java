package dk.sdu.mmmi.swe.gtg.pathfindingcommon.data;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Node> path;
    private float radius = 1.5f;

    public Path(List<Node> path) {
        this.path = path;
    }

    public Path() {
        path = new ArrayList<>();
    }

    public void addNode(Node node) {
        path.add(node);
    }

    public List<Node> getNodes() {
        return path;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
