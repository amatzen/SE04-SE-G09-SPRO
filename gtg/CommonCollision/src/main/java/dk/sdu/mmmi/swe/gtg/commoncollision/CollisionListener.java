package dk.sdu.mmmi.swe.gtg.commoncollision;

import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public abstract class CollisionListener implements ICollisionListener {
    @Override
    public void beginContact(CollisionEntity entityA, CollisionEntity entityB) {

    }

    @Override
    public void endContact(CollisionEntity entityA, CollisionEntity entityB) {

    }

    @Override
    public void preSolve(CollisionEntity entityA, CollisionEntity entityB) {
    }

    @Override
    public void postSolve(CollisionEntity entityA, CollisionEntity entityB, float[] normalImpulses) {
    }

}
