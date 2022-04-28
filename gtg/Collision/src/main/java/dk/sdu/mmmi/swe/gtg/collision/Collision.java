package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Collision implements CollisionSPI, IGamePluginService, com.badlogic.gdx.physics.box2d.ContactListener {

    private List<ICollisionListener> listeners;

    public Collision() {
        listeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void beginContact(Contact contact) {

        listeners.forEach(collisionListener -> {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();

            if (
                    collisionListener.getFamilyA().matches(entityA)
                            && collisionListener.getFamilyB().matches(entityB)
            ) {
                collisionListener.beginContact(contact, entityA, entityB);
            } else if (
                    collisionListener.getFamilyA().matches(entityB)
                            && collisionListener.getFamilyB().matches(entityA)
            ) {
                collisionListener.beginContact(contact, entityB, entityA);
            }

        });

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    @Override
    public void addListener(ICollisionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ICollisionListener collisionListener) {
        listeners.remove(collisionListener);
    }

    @Reference
    private IWorldManager worldManager;

    @Override
    public void start(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(this);
    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(null);
    }
}
