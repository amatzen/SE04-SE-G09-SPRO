package dk.sdu.mmmi.swe.gtg.impactdamagesystem.internal;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;

public class ImpactDamageCollisionListener implements ICollisionListener {
    private IFamily familyA, familyB;

    public ImpactDamageCollisionListener() {
        familyA = Family.builder().with(LifePart.class).get();
        familyB = Family.ALL;
    }

    @Override
    public IFamily getFamilyA() {
        return familyA;
    }

    @Override
    public IFamily getFamilyB() {
        return familyB;
    }

    @Override
    public void beginContact(Contact contact, Entity entityA, Entity entityB) {
    }

    @Override
    public void endContact(Contact contact, Entity entityA, Entity entityB) {
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold, Entity entityA, Entity entityB) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse, Entity entityA, Entity entityB) {

        float sum = 0;

        for (int i = 0; i < contactImpulse.getNormalImpulses().length; i++) {
            sum += contactImpulse.getNormalImpulses()[i];
        }

        // System.out.println("SUM: " + sum);

        LifePart lifePartA = entityA.getPart(LifePart.class);
        LifePart lifePartB = entityB.getPart(LifePart.class);

        if (lifePartB != null) {

        }
    }
}
