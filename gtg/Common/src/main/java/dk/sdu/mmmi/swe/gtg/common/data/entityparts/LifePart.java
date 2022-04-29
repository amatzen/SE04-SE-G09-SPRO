package dk.sdu.mmmi.swe.gtg.common.data.entityparts;

public class LifePart implements IEntityPart {

    private int life = 100;
    
    @Override
    public void destroy() {

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life){
        this.life = life;
    }
  
    public void setDamage(int damage) {
        this.life = life - damage;
    }
}
