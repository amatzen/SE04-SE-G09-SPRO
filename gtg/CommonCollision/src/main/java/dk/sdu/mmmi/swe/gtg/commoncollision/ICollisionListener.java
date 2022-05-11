package dk.sdu.mmmi.swe.gtg.commoncollision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;

public interface ICollisionListener {

    public IFamily getFamilyA();

    public IFamily getFamilyB();

    public void beginContact(Contact contact, Entity entityA, Entity entityB);

    public void endContact(Contact contact, Entity entityA, Entity entityB);

    public void preSolve(Contact contact, Manifold manifold, Entity entityA, Entity entityB);

    public void postSolve(Contact contact, ContactImpulse contactImpulse, Entity entityA, Entity entityB, float[] normalImpulses);

}
