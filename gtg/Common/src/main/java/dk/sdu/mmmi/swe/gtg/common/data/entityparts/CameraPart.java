package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraPart implements EntityPart {

    private OrthographicCamera camera;

    public OrthographicCamera getCamera() {
        return camera;
    }

}
