package dk.sdu.mmmi.swe.gtg.bullet.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.LifePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonbullet.Bullet;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.commoncollision.data.CollisionEntity;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class BulletControlSystem implements BulletSPI, IPlugin {
    private final int bulletDamage = 20;
    @Reference
    private IWorldManager worldManager;
    @Reference
    private IEngine engine;
    @Reference
    private CollisionSPI collisionSPI;
    private ICollisionListener collisionListener;
    private TexturePart bulletTexture;

    public BulletControlSystem() {

    }

    @Override
    public Bullet createBullet(Vector2 bulletPosition, Vector2 direction, Vector2 baseSpeed) {
        Vector2 bulletVelocity = new Vector2(direction);
        bulletVelocity.scl(50f).add(baseSpeed);

        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.bullet = true;
        bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
        bulletBodyDef.fixedRotation = true;

        Body bulletBody = worldManager.createBody(bulletBodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f / 2.0f, 0.2f / 2.0f);
        bulletBody.createFixture(polygonShape, 10.0f);
        polygonShape.dispose();

        bulletBody.setTransform(bulletPosition, bulletBody.getAngle());
        bulletBody.setLinearVelocity(bulletVelocity);

        Bullet bullet = new Bullet();
        TransformPart transformPart = new TransformPart();

        BodyPart bulletBodyPart = new BodyPart(bulletBody);
        bulletBodyPart.getBody().setUserData(bullet);

        transformPart.setScale(1f / 1890f, 1f / 1890f);
        bullet.addPart(getBulletTexture());
        bullet.addPart(transformPart);
        bullet.addPart(bulletBodyPart);


        return bullet;
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

    private TexturePart getBulletTexture() {
        if (this.bulletTexture == null) {
            this.bulletTexture = getTexture("assets/entities/bullet.png");
        }
        return this.bulletTexture;
    }

    @Override
    public void install(GameData gameData) {
        IFamily familyA = Family.builder().forEntities(Bullet.class).get();

        IFamily familyB = Family.ALL;

        collisionListener = new CollisionListener() {
            @Override
            public IFamily getFamilyA() {
                return familyA;
            }

            @Override
            public IFamily getFamilyB() {
                return familyB;
            }

            @Override
            public void beginContact(CollisionEntity bullet, CollisionEntity entityB) {
                if (entityB.isSensorPart()) {
                    return;
                } else {
                    engine.removeEntity(bullet.getEntity());
                }

                if (entityB.getEntity().hasPart(LifePart.class)) {
                    LifePart lifePart = entityB.getEntity().getPart(LifePart.class);
                    lifePart.inflictDamage(bulletDamage);
                }
            }
        };
        collisionSPI.addListener(collisionListener);
    }

    @Override
    public void uninstall(GameData gameData) {
        collisionSPI.removeListener(collisionListener);
    }
}
