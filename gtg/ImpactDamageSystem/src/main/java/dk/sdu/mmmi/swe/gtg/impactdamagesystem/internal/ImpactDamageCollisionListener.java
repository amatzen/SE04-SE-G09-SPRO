package dk.sdu.mmmi.swe.gtg.impactdamagesystem.internal;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.ImpactDamagePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;

public class ImpactDamageCollisionListener extends CollisionListener {
    private IFamily familyA, familyB;

    public ImpactDamageCollisionListener() {
        familyA = Family.builder().with(LifePart.class, ImpactDamagePart.class).get();
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

        float impulseSum = 0;

        for (int i = 0; i < impulses.length; i++) {
            impulseSum += impulses[i];
        }

        if (impulseSum <= 0f) {
            return;
        }

        // Map the impulseSum of range [0, 50000] to the range [0, 100]
        int damage = (int) (impulseSum / 50000f * 100f);

        Body bodyA = entityA.getEntity().getPart(BodyPart.class).getBody();
        Body bodyB = entityB.getEntity().getPart(BodyPart.class).getBody();

        if (
                bodyA.getType() == BodyDef.BodyType.StaticBody ||
                        bodyB.getType() == BodyDef.BodyType.StaticBody
        ) {
            damage *= 0.1f;
        }

        if (damage < 5) {
            return;
        }
        LifePart lifePartA = entityA.getEntity().getPart(LifePart.class);

        lifePartA.inflictDamage(damage);

        ImpactDamageSystem.crashSound.stop();
        ImpactDamageSystem.crashSound.play();

        if (familyA.matches(entityB.getEntity())) {
            LifePart lifePartB = entityB.getEntity().getPart(LifePart.class);
            lifePartB.inflictDamage(damage);
        }
    }
}
