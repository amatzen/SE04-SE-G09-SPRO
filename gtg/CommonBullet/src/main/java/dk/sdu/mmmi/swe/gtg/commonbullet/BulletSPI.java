package dk.sdu.mmmi.swe.gtg.commonbullet;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Public API representing an example OSGi service
 */
public interface BulletSPI
{
    // public methods go here...
    Body createBullet(); // might change to
}