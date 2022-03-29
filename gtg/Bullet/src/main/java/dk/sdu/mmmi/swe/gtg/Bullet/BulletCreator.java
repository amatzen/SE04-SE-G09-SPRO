package dk.sdu.mmmi.swe.gtg.Bullet;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class BulletCreator implements BulletSPI {

    @Reference
    private IWorldManager worldManager;

    private Body pBody;

    @Override
    public Body createBullet() {
        System.out.println("skrr");
        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.bullet=true;
        bulletBodyDef.type=BodyDef.BodyType.DynamicBody;
        bulletBodyDef.fixedRotation=true;
        pBody = worldManager.createBody(bulletBodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(32/2,32/2);
        pBody.createFixture(polygonShape,1.0f);
        polygonShape.dispose();
               return pBody ;
    }
}
