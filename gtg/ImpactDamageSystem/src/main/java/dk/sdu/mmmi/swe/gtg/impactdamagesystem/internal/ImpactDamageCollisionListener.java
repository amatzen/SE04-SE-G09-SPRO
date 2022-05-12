package dk.sdu.mmmi.swe.gtg.impactdamagesystem.internal;

import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public class ImpactDamageCollisionListener extends CollisionListener {
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
    public void postSolve(CollisionEntity entityA, CollisionEntity entityB, float[] normalImpulses) {

        float[] impulses = normalImpulses;

        float sum = 0;

        for (int i = 0; i < impulses.length; i++) {
            sum += impulses[i];
        }

        if (sum <= 0f) {
            return;
        }

        // Map the sum of range [0, 100000] to the range [0, 100]
        int damage = (int) (sum / 100000f * 100f);

        if (damage < 5) {
            return;
        }
        LifePart lifePartA = entityA.getEntity().getPart(LifePart.class);
        LifePart lifePartB = entityB.getEntity().getPart(LifePart.class);

        lifePartA.inflictDamage(damage);

        if (lifePartB != null) {
            lifePartB.inflictDamage(damage);
        }
    }
}
