package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.*;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IProcessingSystem;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import static dk.sdu.mmmi.swe.gtg.common.utilities.Utility.containsNull;

@Component
public class CollisionSystem implements CollisionSPI, IPlugin, ContactListener, IProcessingSystem {

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
    public void install(GameData gameData) {
        this.worldManager.setContactLister(this);
    }

    @Override
    public void uninstall(GameData gameData) {
        listeners.clear();
        contacts.clear();
        this.worldManager.setContactLister(null);
    }

    @Override
    public void addedToEngine() {
    }

    @Override
    public void process(GameData gameData) {
        while (!contacts.isEmpty()) {
            Collision collision = contacts.poll();

            CollisionEntity collisionEntityA = collision.getCollisionEntityA();
            CollisionEntity collisionEntityB = collision.getCollisionEntityB();

            if (containsNull(
                    collisionEntityA.getEntity(),
                    collisionEntityB.getEntity())
            ) {
                continue;
            }

            for(ICollisionListener collisionListener : listeners) {

                if (
                        collisionListener.getFamilyA().matches(collisionEntityA.getEntity())
                                && collisionListener.getFamilyB().matches(collisionEntityB.getEntity())
                ) {
                    // Do nothing
                } else if (
                        collisionListener.getFamilyA().matches(collisionEntityB.getEntity())
                                && collisionListener.getFamilyB().matches(collisionEntityA.getEntity())
                ) {
                    // Swap entities
                    CollisionEntity temp = collisionEntityA;
                    collisionEntityA = collisionEntityB;
                    collisionEntityB = temp;
                } else {
                    continue;
                }

                Contact contact = collision.getContact();

                switch (collision.getContactType()) {
                    case BEGIN:
                        collisionListener.beginContact(
                                collisionEntityA, collisionEntityB
                        );
                        break;
                    case END:
                        collisionListener.endContact(
                                collisionEntityA, collisionEntityB
                        );
                        break;
                    case PRESOLVE:
                        collisionListener.preSolve(
                                collisionEntityA, collisionEntityB
                        );
                        break;
                    case POSTSOLVE:
                        collisionListener.postSolve(
                                collisionEntityA, collisionEntityB,
                                collision.getNormalImpulses()
                        );
                        break;
                    default:
                        throw new IllegalStateException("Unknown contact type: " + collision.getContactType());
                }
            }
        }
    }
}
