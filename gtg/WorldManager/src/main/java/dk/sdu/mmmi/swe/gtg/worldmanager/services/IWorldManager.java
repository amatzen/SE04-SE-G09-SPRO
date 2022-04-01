package dk.sdu.mmmi.swe.gtg.worldmanager.services;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.*;

public interface IWorldManager {
    // public methods go here...

    void clearWorld();

    Body createBody(BodyDef def);

    Joint createJoint(JointDef def);

    void render(Box2DDebugRenderer renderer, Matrix4 projectionMatrix);

    void setContactLister(ContactListener contactLister);
}
