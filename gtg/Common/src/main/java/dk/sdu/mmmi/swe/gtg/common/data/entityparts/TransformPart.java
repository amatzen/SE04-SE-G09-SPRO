package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.math.Vector3;

public class TransformPart implements IEntityPart {

    private final Vector3 position = new Vector3();
    private float rotation = 0.0f;

    public void setPosition(float x, float y){
        setPosition(x, y, this.position.z);
    }

    public void setPosition(float x, float y, float z){
        this.position.set(x, y, z);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setRotation(float rot){
        this.rotation = rot;
    }

    @Override
    public void destroy() {

    }
}
