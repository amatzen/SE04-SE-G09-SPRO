package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Collision {

    private Contact contact;
    private ContactType contactType;
    private ContactImpulse contactImpulse;
    private float[] normalImpulses;
    private Manifold manifold;

    public Collision(Contact contact, ContactImpulse contactImpulse, Manifold manifold, ContactType contactType, float[] normalImpulses) {
        this(contactType);
        this.contact = contact;
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
    }

    public Collision(Contact contact, ContactImpulse contactImpulse, float[] normalImpulses) {
        this(ContactType.POSTSOLVE);
        this.contact = contact;
        this.contactImpulse = contactImpulse;
        this.normalImpulses = normalImpulses;
    }

    public Collision(Contact contact, Manifold manifold) {
        this(ContactType.PRESOLVE);
        this.contact = contact;
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

    public enum ContactType {
        BEGIN,
        END,
        PRESOLVE,
        POSTSOLVE,
    }

}
