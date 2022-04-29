package dk.sdu.mmmi.swe.gtg.collision;

import com.badlogic.gdx.physics.box2d.Contact;

public class Collision {

    private Contact contact;
    private ContactType contactType;

    public Collision(Contact contact, ContactType contactType) {
        this.contact = contact;
        this.contactType = contactType;
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

    public enum ContactType {
        BEGIN,
        END
    }

}
