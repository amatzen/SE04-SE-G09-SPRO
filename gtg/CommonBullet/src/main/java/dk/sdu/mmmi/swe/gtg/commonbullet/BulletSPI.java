package dk.sdu.mmmi.swe.gtg.commonbullet;

import com.badlogic.gdx.math.Vector2;

public interface BulletSPI {
    Bullet createBullet(Vector2 bulletPosition, Vector2 direction, Vector2 baseSpeed);
}