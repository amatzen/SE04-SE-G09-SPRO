package dk.sdu.mmmi.swe.gtg.worldmanager.services;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;

/**
 * Public API representing an example OSGi service
 */
public interface IWorldManager
{
    // public methods go here...

    void clearWorld();

    Body createBody(BodyDef def);

    Joint createJoint(JointDef def);
}

