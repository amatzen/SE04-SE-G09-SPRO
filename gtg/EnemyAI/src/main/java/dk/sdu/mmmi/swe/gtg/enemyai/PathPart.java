package dk.sdu.mmmi.swe.gtg.enemyai;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;
import dk.sdu.mmmi.swe.gtg.enemyai.internal.AStar;

import java.util.List;

public class PathPart implements IEntityPart {
    private List<Node> path;
    private int currentPathIndex;

    public PathPart(List<Node> paths) {
        this.path = paths;
        currentPathIndex = 0;
    }

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public int getCurrentPathIndex() {
        return currentPathIndex;
    }

    public void setCurrentPathIndex(int currentPathIndex) {
        this.currentPathIndex = currentPathIndex;
    }

    public int getCurrentIndex() {
        return currentPathIndex;
    }

    @Override
    public void destroy() {

    }
}
