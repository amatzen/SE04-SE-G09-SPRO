package dk.sdu.mmmi.swe.gtg.commoncollision;

public interface CollisionSPI {
    void addListener(ICollisionListener listener);

    void removeListener(ICollisionListener collisionListener);
}
