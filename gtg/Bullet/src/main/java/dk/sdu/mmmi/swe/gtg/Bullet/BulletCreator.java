package dk.sdu.mmmi.swe.gtg.Bullet;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;

public class BulletCreator implements BulletSPI {
    @Override
    public Entity createBullet() {
        System.out.println("skrr");
        return null;
    }
}
