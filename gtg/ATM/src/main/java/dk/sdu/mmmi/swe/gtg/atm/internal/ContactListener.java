package dk.sdu.mmmi.swe.gtg.atm.internal;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.atm.ATM;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (carAtmCollision(fixtureA, fixtureB)) {
            System.out.println("Collision with car and ATM");
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (carAtmCollision(fixtureA, fixtureB)) {
            System.out.println("Collision with car and ATM stopped");
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private boolean carAtmCollision(Fixture a, Fixture b) {
        if (a.getBody().getUserData() instanceof Vehicle || b.getBody().getUserData() instanceof Vehicle) {
            return a.getBody().getUserData() instanceof ATM || b.getBody().getUserData() instanceof ATM;
        }
        return false;
    }

}
