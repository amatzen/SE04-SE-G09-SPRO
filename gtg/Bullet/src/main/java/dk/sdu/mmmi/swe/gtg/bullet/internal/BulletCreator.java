package dk.sdu.mmmi.swe.gtg.bullet.internal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.lwjgl.util.vector.Vector2f;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class BulletCreator implements BulletSPI {

    @Reference
    private IWorldManager worldManager;

    private Body pBody;

    @Override
    public Body createBullet(Vector2 bulletPosition, Vector2 baseSpeed) {
       Vector2 bulletVelocity = new Vector2(baseSpeed.nor());
       bulletVelocity.scl(50f);

        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.bullet=true;
        bulletBodyDef.type=BodyDef.BodyType.DynamicBody;
        bulletBodyDef.fixedRotation=true;
        pBody = worldManager.createBody(bulletBodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f / 2.0f, 0.2f /2.0f);
        pBody.createFixture(polygonShape,10.0f);
        polygonShape.dispose();
        pBody.setTransform(bulletPosition, pBody.getAngle());
        pBody.setLinearVelocity(bulletVelocity);
               return pBody ;
    }
}
