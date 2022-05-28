package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformPart implements IEntityPart {

    private final Vector2 origin = new Vector2();
    private Vector2 position;
    private float z;
    private Vector2 scale = new Vector2(1, 1);
    private float rotation = 0.0f;

    public TransformPart() {
        this.position = new Vector2();
        this.z = 0f;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector2(x, y);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y);
        this.z = z;
    }

    public void setPosition(Vector2 position, float z) {
        this.position = position;
        this.z = z;
    }

    public Vector3 getPosition() {
        return new Vector3(position.x, position.y, z);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition2() {
        return this.position;
    }

    public void setOrigin(float x, float y) {
        this.origin.set(x, y);
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rot) {
        this.rotation = rot;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(float x, float y) {
        this.scale.set(x, y);
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void destroy() {

    }
}
