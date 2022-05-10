package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import static dk.sdu.mmmi.swe.gtg.common.utilities.Utility.containsNull;

@Component
public class CollisionSystem implements CollisionSPI, IPlugin, com.badlogic.gdx.physics.box2d.ContactListener, IProcessingSystem {

    private final List<ICollisionListener> listeners;

    private final Queue<Collision> contacts;
    @Reference
    private IWorldManager worldManager;

    public CollisionSystem() {
        listeners = new CopyOnWriteArrayList<>();
        contacts = new LinkedList<>();
    }

    @Override
    public void beginContact(Contact contact) {
        contacts.add(new Collision(contact, Collision.ContactType.BEGIN));
    }

    @Override
    public void endContact(Contact contact) {
        contacts.add(new Collision(contact, Collision.ContactType.END));
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        contacts.add(new Collision(contact, manifold));
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        contacts.add(new Collision(contact, contactImpulse, contactImpulse.getNormalImpulses()));
    }

    @Override
    public void addListener(ICollisionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ICollisionListener collisionListener) {
        listeners.remove(collisionListener);
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(this);
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(null);
    }

    @Override
    public void addedToEngine(IEngine engine) {

    }

    @Override
    public void process(GameData gameData) {
        while (!contacts.isEmpty()) {
            Collision collision = contacts.poll();
            Contact contact = collision.getContact();

            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

            if (containsNull(fixtureA, fixtureB)) {
                continue;
            }

            Entity entityA = (Entity) fixtureA.getBody().getUserData();
            Entity entityB = (Entity) fixtureB.getBody().getUserData();

            if (containsNull(entityA, entityB)) {
                continue;
            }

            for(ICollisionListener collisionListener : listeners) {

                if (
                        collisionListener.getFamilyA().matches(entityA)
                                && collisionListener.getFamilyB().matches(entityB)
                ) {
                    // Do nothing
                } else if (
                        collisionListener.getFamilyA().matches(entityB)
                                && collisionListener.getFamilyB().matches(entityA)
                ) {
                    // Swap entities
                    Entity temp = entityA;
                    entityA = entityB;
                    entityB = temp;
                } else {
                    continue;
                }

                switch (collision.getContactType()) {
                    case BEGIN:
                        collisionListener.beginContact(contact, entityA, entityB);
                        break;
                    case END:
                        collisionListener.endContact(contact, entityA, entityB);
                        break;
                    case PRESOLVE:
                        collisionListener.preSolve(contact, collision.getManifold(), entityA, entityB);
                        break;
                    case POSTSOLVE:
                        collisionListener.postSolve(contact, collision.getContactImpulse(), entityA, entityB, collision.getNormalImpulses());
                        break;
                    default:
                        throw new IllegalStateException("Unknown contact type: " + collision.getContactType());
                }
            }
        }
    }
}
