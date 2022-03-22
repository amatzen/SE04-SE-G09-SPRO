package dk.sdu.mmmi.swe.gtg.worldmanager.internal;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;

public class WorldManager implements IWorldManager {

    private World world;

    private Vector2 gravity;

    public WorldManager() {
        gravity = new Vector2(0, 0);
        world = new World(gravity, true);
    }

    public void clearWorld() {
        if (world != null) {
            world.dispose();
        }

        world = new World(gravity, true);
    }

    @Override
    public void update(float delta) {
        world.step(delta, 8, 3);
    }

    public Body createBody(BodyDef def) {
        return world.createBody(def);
    }

    public Joint createJoint(JointDef def) {
        return world.createJoint(def);
    }

    @Override
    public void render(Box2DDebugRenderer renderer, Matrix4 projectionMatrix) {
        renderer.render(world, projectionMatrix);
    }

}
