package dk.sdu.mmmi.swe.gtg.enemyai;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

import java.util.List;

public class PathPart implements IEntityPart {
    private Path path;
    private int currentPathIndex;

    public PathPart(Path paths) {
        this.path = paths;
        currentPathIndex = 0;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
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
