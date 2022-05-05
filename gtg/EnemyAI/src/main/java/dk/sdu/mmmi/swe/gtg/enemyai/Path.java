package dk.sdu.mmmi.swe.gtg.enemyai;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Node> path;
    private float radius = 20;

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

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
