package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.Collection;

public class Collision {

    private Contact contact;
    private ContactType contactType;

    private ContactImpulse contactImpulse;

    private Manifold manifold;

    public Collision(Contact contact, ContactImpulse contactImpulse, Manifold manifold, ContactType contactType) {
        this(contactType);
        this.contact = contact;
        this.contactImpulse = contactImpulse;
        this.manifold = manifold;
    }

    public Collision(ContactType contactType) {
        this.contactType = contactType;
    }

    public Collision(Contact contact, ContactType contactType) {
        this(contactType);
        this.contact = contact;
    }

    public Collision(Contact contact, ContactImpulse contactImpulse) {
        this(ContactType.POSTSOLVE);
        this.contact = contact;
        this.contactImpulse = contactImpulse;
    }

    public Collision(Contact contact, Manifold manifold) {
        this(ContactType.PRESOLVE);
        this.contact = contact;
        this.manifold = manifold;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Manifold getManifold() {
        return manifold;
    }

    public void setManifold(Manifold manifold) {
        this.manifold = manifold;
    }

    public ContactImpulse getContactImpulse() {
        return contactImpulse;
    }

    public void setContactImpulse(ContactImpulse contactImpulse) {
        this.contactImpulse = contactImpulse;
    }

    public enum ContactType {
        BEGIN,
        END,
        PRESOLVE,
        POSTSOLVE,
    }

}
