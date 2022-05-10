package dk.sdu.mmmi.swe.gtg.bullet.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.*;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commonbullet.Bullet;
import dk.sdu.mmmi.swe.gtg.commonbullet.BulletSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class BulletCreator implements BulletSPI, IPlugin {

    @Reference
    private IWorldManager worldManager;

    private Body pBody;

    @Reference
    private CollisionSPI collisionSPI;

    private ICollisionListener collisionListener;

    public BulletCreator() {

    }

    @Override
    public Bullet createBullet(Vector2 bulletPosition, Vector2 direction, Vector2 baseSpeed) {
        Vector2 bulletVelocity = new Vector2(direction);
        bulletVelocity.scl(50f).add(baseSpeed);

        BodyDef bulletBodyDef = new BodyDef();
        bulletBodyDef.bullet = true;
        bulletBodyDef.type = BodyDef.BodyType.DynamicBody;
        bulletBodyDef.fixedRotation = true;

        pBody = worldManager.createBody(bulletBodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.2f / 2.0f, 0.2f / 2.0f);
        pBody.createFixture(polygonShape, 10.0f);
        polygonShape.dispose();

        pBody.setTransform(bulletPosition, pBody.getAngle());
        pBody.setLinearVelocity(bulletVelocity);

        Bullet bullet = new Bullet();
        TransformPart transformPart = new TransformPart();

        BodyPart bulletBody = new BodyPart(pBody);
        bulletBody.getBody().setUserData(bullet);

        transformPart.setScale(1f / 1890f, 1f / 1890f);
        bullet.addPart(getBulletTexture());
        bullet.addPart(transformPart);
        bullet.addPart(bulletBody);


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
        return getTexture("assets/bullet.png");
    }

    @Override
    public void install(IEngine engine, GameData gameData) {
        IFamily familyA = Family.builder().forEntities(Bullet.class).get();

        IFamily familyB = Family.ALL;

        collisionListener = new ICollisionListener() {
            @Override
            public IFamily getFamilyA() {
                return familyA;
            }

            @Override
            public IFamily getFamilyB() {
                return familyB;
            }

            @Override
            public void beginContact(Contact contact, Entity entityA, Entity entityB) {
                if (entityB.hasPart(SensorPart.class)) {
                    return;
                } else {
                    engine.removeEntity(entityA);
                }

                if (entityB.hasPart(LifePart.class)) {
                    LifePart lifePart = entityB.getPart(LifePart.class);
                    lifePart.inflictDamage(10);
                    System.out.println("Health: " + lifePart.getLife());

                    if (lifePart.getLife() <= 0) {
                        System.out.println("Game over");
                        engine.removeEntity(entityB); // Removes vehicle
                    }
                }


            }

            @Override
            public void endContact(Contact contact, Entity entityA, Entity entityB) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold, Entity entityB, Entity entityA) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse, Entity entityB, Entity entityA, float[] normalImpulses) {

            }

        };
        collisionSPI.addListener(collisionListener);
    }

    @Override
    public void uninstall(IEngine engine, GameData gameData) {
        collisionSPI.removeListener(collisionListener);
    }
}
