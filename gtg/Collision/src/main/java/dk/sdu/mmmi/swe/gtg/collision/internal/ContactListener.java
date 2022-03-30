package dk.sdu.mmmi.swe.gtg.collision.internal;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.collision.Collision;
import dk.sdu.mmmi.swe.gtg.vehicle.Vehicle;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // System.out.println("Collision occurred!");

        if (carAtmCollision(fixtureA, fixtureB)) {
            Vehicle car = (Vehicle) fixtureA.getUserData();
            Collision atm = (Collision) fixtureB.getUserData();
            System.out.println("Collision with car and ATM");
        } else {
            System.out.println("Collision, but not with car and ATM");
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println("Collision stopped!");

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    private boolean carAtmCollision(Fixture a, Fixture b) {
        if (a.getUserData() instanceof Vehicle || b.getUserData() instanceof Vehicle) {
            return a.getUserData() instanceof Collision || b.getUserData() instanceof Collision;
        }
        return false;
    }

}
