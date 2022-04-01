package dk.sdu.mmmi.swe.gtg.commonbullet;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Public API representing an example OSGi service
 */
public interface BulletSPI
{
    // public methods go here...
    Bullet createBullet(Vector2 bulletPosition, Vector2 direction, Vector2 baseSpeed); // might change to
}