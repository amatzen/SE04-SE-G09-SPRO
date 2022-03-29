package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformPart implements IEntityPart {

    private final Vector3 position = new Vector3();
    private final Vector2 scale = new Vector2(1, 1);
    private final Vector2 origin = new Vector2();

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

    public void setOrigin(float x, float y){
        this.origin.set(x, y);
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setRotation(float rot){
        this.rotation = rot;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(float x, float y){
        this.scale.set(x, y);
    }

    @Override
    public void destroy() {

    }
}
