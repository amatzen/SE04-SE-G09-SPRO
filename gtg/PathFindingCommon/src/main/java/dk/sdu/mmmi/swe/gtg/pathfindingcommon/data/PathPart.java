package dk.sdu.mmmi.swe.gtg.pathfindingcommon.data;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.IEntityPart;

public class PathPart implements IEntityPart {
    private Path path;

    public PathPart(Path paths) {
        this.path = paths;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public void destroy() {

    }
}
