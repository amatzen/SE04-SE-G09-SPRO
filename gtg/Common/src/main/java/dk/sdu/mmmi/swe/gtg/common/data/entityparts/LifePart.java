package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class LifePart implements IEntityPart {

    private int life;

    @Override
    public void destroy() {

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life, int damage) {
        this.life = life - damage;

    }
}
