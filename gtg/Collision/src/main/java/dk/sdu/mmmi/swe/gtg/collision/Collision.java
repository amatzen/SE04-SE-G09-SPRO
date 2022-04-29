package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.services.entity.IEntityProcessingService;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IGamePluginService;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Collision implements CollisionSPI, IGamePluginService, com.badlogic.gdx.physics.box2d.ContactListener, IEntityProcessingService {

    private final List<ICollisionListener> listeners;

    private final Queue<Contact> contacts;
    @Reference
    private IWorldManager worldManager;

    public Collision() {
        listeners = new CopyOnWriteArrayList<>();
        contacts = new LinkedList<>();
    }

    @Override
    public void beginContact(Contact contact) {
        contacts.add(contact);
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

    @Override
    public void start(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(this);
    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        this.worldManager.setContactLister(null);
    }

    @Override
    public void addedToEngine(IEngine engine) {

    }

    @Override
    public void process(GameData gameData) {
        while (!contacts.isEmpty()) {
            Contact contact = contacts.poll();

            listeners.forEach(collisionListener -> {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA == null || fixtureB == null) {
                    return;
                }

                Entity entityA = (Entity) fixtureA.getBody().getUserData();
                Entity entityB = (Entity) fixtureB.getBody().getUserData();

                if (entityA == null || entityB == null) {
                    return;
                }

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
    }
}
