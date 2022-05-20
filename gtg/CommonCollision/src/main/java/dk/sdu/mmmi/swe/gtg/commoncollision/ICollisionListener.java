package dk.sdu.mmmi.swe.gtg.commoncollision;

import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public interface ICollisionListener {

     IFamily getFamilyA();

     IFamily getFamilyB();

     void beginContact(CollisionEntity entityA, CollisionEntity entityB);

     void endContact(CollisionEntity entityA, CollisionEntity entityB);

     void preSolve(CollisionEntity entityA, CollisionEntity entityB);

     void postSolve(CollisionEntity entityA, CollisionEntity entityB, float[] normalImpulses);

}
