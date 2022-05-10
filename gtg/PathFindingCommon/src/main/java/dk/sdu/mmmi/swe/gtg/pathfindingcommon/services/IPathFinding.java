package dk.sdu.mmmi.swe.gtg.pathfindingcommon.services;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.swe.gtg.commonmap.MapSPI;
import dk.sdu.mmmi.swe.gtg.pathfindingcommon.data.Path;

public interface IPathFinding {

    Path searchNodePath(Vector2 from, Vector2 to, MapSPI map);

    Path searchNodePath(Vector2 from, Vector2 to, MapSPI map, int resolution);
}
