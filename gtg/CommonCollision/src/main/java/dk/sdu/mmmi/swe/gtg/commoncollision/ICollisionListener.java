package dk.sdu.mmmi.swe.gtg.commoncollision;

import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public interface ICollisionListener {

     IFamily getFamilyA();

    public IFamily getFamilyB();

    public void beginContact(CollisionEntity entityA, CollisionEntity entityB);

    public void endContact(CollisionEntity entityA, CollisionEntity entityB);

    public void preSolve(CollisionEntity entityA, CollisionEntity entityB);

    public void postSolve(CollisionEntity entityA, CollisionEntity entityB, float[] normalImpulses);

}
