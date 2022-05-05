package dk.sdu.mmmi.swe.gtg.atm.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import dk.sdu.mmmi.swe.gtg.atm.ATM;
import dk.sdu.mmmi.swe.gtg.common.data.Entity;
import dk.sdu.mmmi.swe.gtg.common.data.GameData;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.BodyPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.SensorPart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TexturePart;
import dk.sdu.mmmi.swe.gtg.common.data.entityparts.TransformPart;
import dk.sdu.mmmi.swe.gtg.common.family.Family;
import dk.sdu.mmmi.swe.gtg.common.family.IFamily;
import dk.sdu.mmmi.swe.gtg.common.services.managers.IEngine;
import dk.sdu.mmmi.swe.gtg.common.services.plugin.IPlugin;
import dk.sdu.mmmi.swe.gtg.commoncollision.CollisionSPI;
import dk.sdu.mmmi.swe.gtg.commoncollision.ICollisionListener;
import dk.sdu.mmmi.swe.gtg.shapefactorycommon.services.ShapeFactorySPI;
import dk.sdu.mmmi.swe.gtg.worldmanager.services.IWorldManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class ATMPlugin implements IPlugin {

    @Reference
    private ShapeFactorySPI shapeFactory;

    @Reference
    private IWorldManager worldManager;

    private ATM atm;

    @Reference
    private CollisionSPI collisionSPI;

    private ICollisionListener collisionListener;

    @Override
    public void start(IEngine engine, GameData gameData) {

        Vector2 atmPosition = new Vector2(137.45f, 84f);
        Vector2 atmSize = new Vector2(1, 1.5f);
        float sensorRadius = 5;

        BodyPart atmBody = new BodyPart(shapeFactory.createRectangle(
                atmPosition, atmSize, BodyDef.BodyType.StaticBody,
                1,
                false));

        SensorPart sensorPart = new SensorPart(shapeFactory.createCircle(
                atmPosition, sensorRadius, BodyDef.BodyType.StaticBody,
                1,
                true));

        this.atm = new ATM();

        atmBody.getBody().setUserData(this.atm);

        sensorPart.getBody().setUserData(this.atm);

        this.atm.addPart(atmBody);
        this.atm.addPart(sensorPart);

        TransformPart transformPart = new TransformPart();
        transformPart.setScale(1f / 184f, 1.5f / 423f);
        this.atm.addPart(transformPart);
        this.atm.addPart(getBodyTexture());

        engine.addEntity(this.atm);

        IFamily familyA = Family.builder().forEntities(ATM.class).get();

        IFamily familyB = Family.builder().get();

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
                System.out.println("Collision with ATM");
            }

            @Override
            public void endContact(Contact contact, Entity entityA, Entity entityB) {
                System.out.println("Collision with ATM stopped");
            }

            @Override
            public void preSolve(Contact contact) {

            }

            @Override
            public void postSolve(Contact contact) {

            }
        };
        collisionSPI.addListener(collisionListener);
    }

    private TexturePart getTexture(String path) {
        final TexturePart texturePart = new TexturePart();
        FileHandle file = Gdx.files.internal(path);
        Texture texture = new Texture(file);
        TextureRegion textureRegion = new TextureRegion(texture);

        texturePart.setRegion(textureRegion);

        return texturePart;
    }

    private TexturePart getBodyTexture() {
        return getTexture("assets/atm.png");
    }

    @Override
    public void stop(IEngine engine, GameData gameData) {
        engine.removeEntity(atm);
        collisionSPI.removeListener(collisionListener);
    }

}
