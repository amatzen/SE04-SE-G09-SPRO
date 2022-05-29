package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

import dk.sdu.mmmi.swe.gtg.common.signals.ISignal;
import dk.sdu.mmmi.swe.gtg.common.signals.Signal;

public class LifePart implements IEntityPart {
    public final ISignal<Integer> onDamage;
    private int life;

    public LifePart(int life) {
        this.life = life;
        onDamage = new Signal<>();
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
