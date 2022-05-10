package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.signals.ISignal;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

public class LifePart implements IEntityPart {
    private int life;

    public final ISignal<Integer> onDamage;
    public final ISignal<Integer> onDeath;

    public LifePart(int life) {
        this.life = life;
        onDamage = new Signal<>();
        onDeath = new Signal<>();
    }

    public LifePart() {
        this(100);
    }

    @Override
    public void destroy() {
        onDamage.dispose();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void inflictDamage(int damage) {
        this.life = life - damage;
        onDamage.fire(damage);
    }
}
