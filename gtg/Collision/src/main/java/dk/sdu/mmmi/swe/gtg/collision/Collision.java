package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public class Collision {

    private Contact contact;
    private ContactType contactType;
    private ContactImpulse contactImpulse;
    private float[] normalImpulses;
    private Manifold manifold;

    private CollisionEntity collisionEntityA;
    private CollisionEntity collisionEntityB;

    public Collision(Contact contact, ContactImpulse contactImpulse, Manifold manifold, ContactType contactType, float[] normalImpulses) {
        this(contact, contactType);
        this.contactImpulse = contactImpulse;
        this.manifold = manifold;
        this.normalImpulses = normalImpulses;
    }

    public Collision(ContactType contactType) {
        this.contactType = contactType;
    }

    public Collision(Contact contact, ContactType contactType) {
        this(contactType);
        this.contact = contact;

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Entity entityA = (Entity) fixtureA.getBody().getUserData();
        Entity entityB = (Entity) fixtureB.getBody().getUserData();

        collisionEntityA = new CollisionEntity(entityA, fixtureA.isSensor());
        collisionEntityB = new CollisionEntity(entityB, fixtureB.isSensor());
    }

    public Collision(Contact contact, ContactImpulse contactImpulse, float[] normalImpulses) {
        this(contact, ContactType.POSTSOLVE);
        this.contactImpulse = contactImpulse;
        this.normalImpulses = normalImpulses;
    }

    public Collision(Contact contact, Manifold manifold) {
        this(contact, ContactType.PRESOLVE);
        this.manifold = manifold;
    }

    public Contact getContact() {
        return contact;
    }


    public ContactType getContactType() {
        return contactType;
    }


    public Manifold getManifold() {
        return manifold;
    }


    public ContactImpulse getContactImpulse() {
        return contactImpulse;
    }


    public float[] getNormalImpulses() {
        return normalImpulses;
    }

    public CollisionEntity getCollisionEntityA() {
        return collisionEntityA;
    }

    public CollisionEntity getCollisionEntityB() {
        return collisionEntityB;
    }

    public enum ContactType {
        BEGIN,
        END,
        PRESOLVE,
        POSTSOLVE,
    }

}
