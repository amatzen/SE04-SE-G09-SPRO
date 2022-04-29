package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class LifePart implements IEntityPart {

    private int life = 100;
    private int life;

    @Override
    public void destroy() {

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life){
        this.life= life;
    }
    public void setDamage(int damage){
        this.life = life-damage;
      
    public void setLife(int life, int damage) {
        this.life = life - damage;

    }
}
